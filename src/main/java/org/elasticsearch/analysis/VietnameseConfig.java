package org.elasticsearch.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugin.analysis.vi.AnalysisVietnamesePlugin;

public class VietnameseConfig {
    public static final String DEFAULT_DICT_PATH = "/usr/local/share/tokenizer/dicts";
    public final String dictPath;
    public final Boolean keepPunctuation;
    public final Boolean splitHost;
    public final Boolean splitURL;
    private static final String pluginBase = AnalysisVietnamesePlugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private static final Path safePath = Paths.get(new File(pluginBase).getParentFile().getAbsoluteFile().toURI());


    public VietnameseConfig(Settings settings) {
        String path;
        try (FileInputStream is = new FileInputStream(safePath.resolve("coccoc-tokenizer.properties").toFile())) {
            Properties configPro = new Properties();
            configPro.load(is);
            path = configPro.getProperty("dict_path", DEFAULT_DICT_PATH);
        } catch (Exception e) {
            path = settings.get("dict_path", DEFAULT_DICT_PATH);
        }
        dictPath = path;
        keepPunctuation = settings.getAsBoolean("keep_punctuation", Boolean.FALSE);
        splitHost = settings.getAsBoolean("split_host", Boolean.FALSE);
        splitURL = settings.getAsBoolean("split_url", Boolean.FALSE);
    }

}
