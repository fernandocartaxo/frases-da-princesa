package com.ctx.frasesdaprincesa.frase;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FraseContent {

    public static final List<Frase> ITEMS = new ArrayList<Frase>();

    public static final Map<String, Frase> ITEM_MAP = new HashMap<String, Frase>();

    public static void addItem(Frase item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class Frase {
        public final String id;
        public final Integer ordem;
        public final String resumo;
        public final Drawable icone;
        public final int audio;

        public Frase(String id, Integer ordem, String resumo, Drawable icone, int audio) {
            this.id = id;
            this.ordem = ordem;
            this.resumo = resumo;
            this.icone = icone;
            this.audio = audio;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
