package main;

import org.junit.Assert;
import org.junit.Test;

public class IndentDebugQueryComponentTest {
    @Test
    public void t() {
        String query = "+(((name:ipod)~0.5 (name:70)~0.5)~2) (name:\"ipod 70\"~3)~0.5 (name:\"ipod 70\"~3)~0.5";
        String indentedExpected = "+(\n" +
                "\t(\n" +
                "\t\t(name:ipod)~0.5\n" +
                "\t\t(name:70)~0.5\n" +
                "\t)~2\n" +
                ")\n" +
                "(name:\"ipod 70\"~3)~0.5\n" +
                "(name:\"ipod 70\"~3)~0.5";

        String indented = IndentDebugQueryComponent.indent(query);

        Assert.assertEquals(indentedExpected, indented);
    }
}

/*



 */