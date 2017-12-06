package com.mangobloggerandroid.app;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Created by ujjawal on 6/12/17.
 */

public class ContainerHolderSingleton {
    private static ContainerHolder containerHolder;

    /**
     * Utility class; don't instantiate.
     */
    private ContainerHolderSingleton() {
    }

    public static ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    public static void setContainerHolder(ContainerHolder c) {
        containerHolder = c;
    }
}
