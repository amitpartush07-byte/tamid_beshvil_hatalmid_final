package com.example.tamid_beshvil_hatalmid_try1;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
// This line MUST be active (no //)
import com.example.tamid_beshvil_hatalmid_try1.BuildConfig;

public class GeminiManager {
    private static GeminiManager instance;
    private final GenerativeModelFutures model;

    private GeminiManager() {
        GenerativeModel gm = new GenerativeModel(
                "gemini-2.5-flash",
               " AIzaSyAlszzF_aiR24hxP_qaZvIYdCMpIKdpkAw"
        );
        this.model = GenerativeModelFutures.from(gm);
    }

    public static synchronized GeminiManager getInstance() {
        if (instance == null) {
            instance = new GeminiManager();
        }
        return instance;
    }

    public GenerativeModelFutures getModel() {
        return model;
    }
}