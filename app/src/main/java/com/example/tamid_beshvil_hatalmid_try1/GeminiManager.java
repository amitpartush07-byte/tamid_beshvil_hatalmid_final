package com.example.tamid_beshvil_hatalmid_try1;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;

public class GeminiManager {
    private static GeminiManager instance;
    private final GenerativeModelFutures model;

    private GeminiManager() {
        // Initialize the model
        GenerativeModel gm = new GenerativeModel(
                "gemini-1.5-flash",
                BuildConfig.Gemini_API_Key
        );
        // Use GenerativeModelFutures for easier Java integration (async)
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