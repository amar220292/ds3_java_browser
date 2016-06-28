package com.spectralogic.dsbrowser.gui;

import com.airhacks.afterburner.injection.Injector;
import com.spectralogic.dsbrowser.gui.components.settings.SettingsModel;
import com.spectralogic.dsbrowser.gui.services.JobWorkers;
import com.spectralogic.dsbrowser.gui.services.Workers;
import com.spectralogic.dsbrowser.gui.services.logservice.LogService;
import com.spectralogic.dsbrowser.gui.services.savedSessionStore.SavedSessionStore;
import com.spectralogic.dsbrowser.gui.services.settings.SettingsStore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {

    private final static Logger LOG = LoggerFactory.getLogger(Main.class);

    private final Workers workers = new Workers();
    private final JobWorkers jobWorkers = new JobWorkers();
    private SavedSessionStore savedSessionStore = null;
    private SettingsStore settings = null;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Deep Storage Browser v0.0.1::");

        this.settings = SettingsStore.loadSettingsStore(); // Do not log when loading the settings store
        // Create the log service before any logging has started
        final LogService logService = new LogService(this.settings.getLogSettings());

        this.savedSessionStore = SavedSessionStore.loadSavedSessionStore();

        final Logger injectorLogger = LoggerFactory.getLogger("Injector");

        LOG.info("Starting Deep Storage Browser v0.0.1");
        Injector.setLogger(injectorLogger::debug);
        Injector.setModelOrService(LogService.class, logService);
        Injector.setModelOrService(SettingsStore.class, settings);
        Injector.setModelOrService(Workers.class, workers);
        Injector.setModelOrService(JobWorkers.class, jobWorkers);
        Injector.setModelOrService(SavedSessionStore.class, this.savedSessionStore);

        final DeepStorageBrowserView mainView = new DeepStorageBrowserView();

        final Scene mainScene = new Scene(mainView.getView());
        primaryStage.setScene(mainScene);

        primaryStage.show();
    }

    @Override
    public void stop() {
        LOG.info("Starting shutdown process...");
        Injector.forgetAll();
        if (savedSessionStore != null) {
            try {
                SavedSessionStore.saveSavedSessionStore(savedSessionStore);
            } catch (final IOException e) {
                LOG.error("Failed to save session information to the local filesystem", e);
            }
        }
        if (settings != null) {
            try {
                SettingsStore.saveSettingsStore(settings);
            } catch (final IOException e) {
                LOG.error("Failed to save settings information to the local filesystem", e);
            }
        }
        workers.shutdown();
        jobWorkers.shutdown();
        LOG.info("Finished shutting down");
    }
}
