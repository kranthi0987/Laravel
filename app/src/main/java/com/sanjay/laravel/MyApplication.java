package com.sanjay.laravel;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.sanjay.laravel.utils.ConnectivityReceiver;
import com.snatik.storage.Storage;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    private static MyApplication instance;

    public MyApplication() {
        instance = this;
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }
    public static Context getContext() {
        return instance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // init
        Storage storage = new Storage(getApplicationContext());

        Realm.init(this);
        RealmConfiguration mRealmConfiguration = new RealmConfiguration.Builder()
                .name("laravel.realm")
                .schemaVersion(1) // skip if you are not managing
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.getInstance(mRealmConfiguration);
        Realm.setDefaultConfiguration(mRealmConfiguration);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}
