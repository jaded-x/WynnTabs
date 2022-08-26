package net.jaded.wynntabs.tabs.render;

public interface TabRenderingHints {
    default int getRowXOffset() {
        return 0;
    }

    default int getRowYOffset() {
        return 0;
    }
}
