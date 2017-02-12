package main;

import java.util.*;

public class IndentDebugQueryComponent {
    static public String indent(String query) {
        Deque<Element> opened = new LinkedList<>();
        Map<Integer, List<Element>> tree = new TreeMap<>();

        char[] charArray = query.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            switch (c) {
                case '(':
                    opened.add(new Element(i));
                    break;
                case ')':
                    Element pair = opened.removeLast();
                    pair.close = i;
                    put(pair, opened, tree);
                    break;
            }
        }

        for (Map.Entry<Integer, List<Element>> entry : tree.entrySet()) {
            for (Element element : entry.getValue()) {
                if (element.parent != null) {
                    element.parent.descendants.add(element);
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (Element root : tree.get(0)) {
            print(0, root, sb, query);
        }

        return sb.toString().trim();
    }

    public static void main(String[] args) {
    }

    static String[] parenthesis(Element element, String query) {
        String p[] = {"", ""};
        char c = 0;

        for (int i = element.open; i >= 0 && !Character.isSpaceChar((c = query.charAt(i))); i--) {
            if (c == '(' && p[0].contains("("))
                break;
            p[0] += c;
        }
        for (int i = element.close; i < query.length() && !Character.isSpaceChar((c = query.charAt(i))); i++) {

            if (c == ')' && p[1].contains(")"))
                break;
            p[1] += c;
        }

        p[0] = new StringBuilder(p[0]).reverse().toString();
        return p;
    }

    static void print(int level, Element element, StringBuilder sb, String query) {
        String tabs = new String(new char[level]).replace("\0", "\t");
        String[] parenthesis = parenthesis(element, query);
        sb.append(tabs);
        sb.append(parenthesis[0]);
        if (element.descendants.size() > 0) {
            sb.append('\n');
            for (Element descendant : element.descendants) {
                print(level + 1, descendant, sb, query);
            }
            sb.append(tabs);
        } else {
            sb.append(query.substring(element.open + 1, element.close));
        }

        sb.append(parenthesis[1]);
        sb.append('\n');
    }

    static void put(Element pair, Deque<Element> opened, Map<Integer, List<Element>> tree) {
        //add element to the level
        int key = opened.size();
        List<Element> elements = tree.get(key);
        if (elements == null) {
            elements = new ArrayList<>();
            tree.put(key, elements);
        }
        elements.add(pair);

        //set parent to the children
        List<Element> children = tree.get(key + 1);
        if (children != null) {
            for (Element child : children) {
                if (child.parent == null) {
                    child.parent = pair;
                }
            }
        }
    }
}