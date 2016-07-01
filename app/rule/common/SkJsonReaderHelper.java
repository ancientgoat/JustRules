package rule.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import rule.action.SkActions;
import rule.base.SkRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 */
public class SkJsonReaderHelper {

    private Logger log = LoggerFactory.getLogger(SkJsonReaderHelper.class);

    public <C> SkActions readActionFiles(String inFilePath, Class inClazz) {
        return null;
    }

    public <C> SkRules readRulesFiles(String inFilePath, Class inClazz) {
        return null;
    }

    public SkJarFile readJarredJsonFiles(final String inTopFile, final SkJsonFilenameFilter inFilenameFilter) {
        SkJarFile skJarFile = new SkJarFile();
        try {
            final URI uri = new URI(inTopFile);
            final FileSystem fs = FileSystems.newFileSystem(uri, new HashMap<>());
            final JarFile jarFile = new JarFile(fs.toString());
            skJarFile.setJarFile(jarFile);

            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                final String name = entry.getName();
                if (!entry.isDirectory() && inFilenameFilter.accept(name)) {
                    skJarFile.addEntry(entry);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(inTopFile, e);
        }
        return skJarFile;
    }

    public <C> List<C> readJsonFiles(final String inFilePath, final Class inClazz) {
        final SkJsonFilenameFilter filenameFilter = new SkJsonFilenameFilter() {
        };
        return readJsonFiles(inFilePath, inClazz, filenameFilter);
    }

    public <C> List<C> readJsonFiles(final String inFilePath, final Class inClazz, final SkJsonFilenameFilter inFilenameFilter) {
        SkJsonReader jsonReader = null;

        final ObjectMapper objectMapper = JsonMapperHelper.jsonMapper();
        try {
            final ClassPathResource resource = new ClassPathResource(inFilePath);
            final File file = resource.getFile();
            if (!file.exists()) {
                // If not on the disk file system try reading from a jar?
                final String absolutePath = file.getAbsolutePath();
                jsonReader = readJarredJsonFiles(absolutePath, inFilenameFilter);
                if (null != jsonReader && 0 == jsonReader.size()) {
                    throw new IllegalArgumentException(
                                    String.format("File '%s' does NOT exist.", absolutePath));
                }
            } else {
                jsonReader = new SkJsonWalkDir(file, inFilenameFilter);
            }
            if (null != jsonReader) {
                return readAllForListOfC(jsonReader, inClazz, objectMapper);
            }
            return null;
        } catch (Exception e) {
            // log.error(String.format("\n%s", json));
            throw new IllegalArgumentException(e);
        }
    }

    private <C> List<C> readAllForListOfC(final SkJsonReader inJsonReader, final Class inClazz,
                                          final ObjectMapper inObjectMapper) {

        final List<C> listOfC = Lists.newArrayList();
        if (0 == inJsonReader.size()) {
            inJsonReader.stream()
                        .forEach(p -> {
                            try {
                                C thingy = (C)inObjectMapper.readValue(inJsonReader.entryToString(p), inClazz);
                                listOfC.add(thingy);
                            } catch (Exception e) {
                                throw new IllegalArgumentException(e);
                            }
                        });
        }
        return listOfC;
    }
}
