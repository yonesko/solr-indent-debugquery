package main;

import java.util.ArrayList;
import java.util.List;

class Element {
    Element parent;
    List<Element> descendants = new ArrayList<>();
    int open;
    int close;

    public Element() {
    }

    public Element(int open) {
        this.open = open;
    }

    @Override
    public String toString() {
        if (parent == null) {
            return String.format("{open=%d close =%d par={null}}",
                    open, close);
        } else {
            return String.format("{open=%d close=%d par={open=%d close=%d}}",
                    open, close, parent.open, parent.close);
        }
    }
}
