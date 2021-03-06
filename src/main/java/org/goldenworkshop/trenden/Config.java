package org.goldenworkshop.trenden;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;
import org.apache.commons.configuration2.tree.NodeCombiner;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.goldenworkshop.trenden.model.Signal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";
    private static Config INSTANCE = null;
    private Configuration configuration;

    public static Config get() {
        if (INSTANCE == null) {

            try {
                init();
            } catch (ConfigurationException e) {
                throw new RuntimeException("Application configuration did not work", e);
            }
        }

        return INSTANCE;
    }

    private static void init() throws ConfigurationException {
        String appenv = System.getProperty("APPENV");


        FileBasedConfigurationBuilder<PropertiesConfiguration> envProperties =
                buildClassPathFileConfiguration(
                        appenv + "/" + APPLICATION_PROPERTIES_FILE_NAME);
        FileBasedConfigurationBuilder<PropertiesConfiguration> globalProperties =
                buildClassPathFileConfiguration("global/"
                        + APPLICATION_PROPERTIES_FILE_NAME);


        // Create and initialize the node combiner
        NodeCombiner combiner = new OverrideCombiner();
        // Construct the combined configuration
        CombinedConfiguration cc = new CombinedConfiguration(combiner);
        cc.addConfiguration(envProperties.getConfiguration());
        cc.addConfiguration(globalProperties.getConfiguration());


        INSTANCE = new Config();
        INSTANCE.configuration = cc;
    }

    private static FileBasedConfigurationBuilder<PropertiesConfiguration> buildClassPathFileConfiguration(String fileName) {
        Parameters params = new Parameters();
        return new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                .configure(params.properties().setLocationStrategy(new ClasspathLocationStrategy())
                        .setFileName(fileName)
                );
    }

    public String getSignalValue(Signal signal) {
        return configuration.getString("signal." + signal.name());
    }
    public String getEuroInvestorTrendenOmx25Url(){
        return configuration.getString("euroinvestetor.trenden.omx25.url");
    }

    public String getTrendenDatabaseName() {
        return configuration.getString("trenden.database.name");
    }

    public String getRecommendationPeriodCollectionName() {
        return configuration.getString("trenden.collection.recommendation-period.name");
    }
    public String getMongoConnectionUrl(){
        return configuration.getString("mongo.url");
    }

    public String getAuthenticationUrl() {
        return configuration.getString("authentication.url");
    }

    public String getRecommendationCollectionName() {
        return configuration.getString("trenden.collection.recommendation.name");
    }

    public String getTokenForUser(String userId) {
        return configuration.getString("trenden.token."+ userId);
    }
    public int getDefaultInvestmentSize(){
        return Integer.valueOf(configuration.getString("investment.default_size"));
    }
}
