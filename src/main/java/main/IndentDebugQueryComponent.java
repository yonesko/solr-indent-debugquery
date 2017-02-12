package main;

import java.util.*;

public class IndentDebugQueryComponent {
    static String input = "+(((name:ipod)~0.5 (name:70)~0.5)~2) (name:\"ipod 70\"~3)~0.5 (name:\"ipod 70\"~3)~0.5";

    static Map<Integer, List<Element>> tree = new TreeMap<>();
    static Deque<Element> opened = new LinkedList<>();

    public static void main(String[] args) {
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            switch (c) {
                case '(':
                    opened.add(new Element(i));
                    break;
                case ')':
                    Element pair = opened.removeLast();
                    pair.close = i;
                    put(pair);
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

//        for (Map.Entry<Integer, List<Range>> entry : tree.entrySet()) {
//            for (Range root : entry.getValue()) {
//                print(entry.getKey(), root, sb);
//            }
//        }

        for (Element root : tree.get(0)) {
            print(0, root, sb);
        }


        System.out.println(sb.toString());
    }

    static String[] parenthesis(Element element) {
        String p[] = {"", ""};
        char c = 0;

        for (int i = element.open; i >= 0 && !Character.isSpaceChar((c = input.charAt(i))); i--) {
            if (c == '(' && p[0].contains("("))
                break;
            p[0] += c;
        }
        for (int i = element.close; i < input.length() && !Character.isSpaceChar((c = input.charAt(i))); i++) {

            if (c == ')' && p[1].contains(")"))
                break;
            p[1] += c;
        }

        p[0] = new StringBuilder(p[0]).reverse().toString();
        return p;
    }

    static void print(int level, Element element, StringBuilder sb) {
        String tabs = new String(new char[level]).replace("\0", "\t");
        String[] parenthesis = parenthesis(element);
        sb.append(tabs);
        sb.append(parenthesis[0]);
        if (element.descendants.size() > 0) {
            sb.append('\n');
            for (Element descendant : element.descendants) {
                print(level + 1, descendant, sb);
            }
            sb.append(tabs);
        } else {
            sb.append(input.substring(element.open + 1, element.close));
        }

        sb.append(parenthesis[1]);
        sb.append('\n');
    }

    static void put(Element pair) {
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