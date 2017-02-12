package main;

import org.junit.Test;

public class IndentDebugQueryComponentTest {
    @Test
    public void t() {

    }
}

/*
+(((name:ipod)~0.5 (name:70)~0.5)~2) (name:"ipod 70"~3)~0.5 (name:"ipod 70"~3)~0.5

+(
	(
		(name:ipod)~0.5
		(name:70)~0.5
	)~2
)
(name:"ipod 70"~3)~0.5
(name:"ipod 70"~3)~0.5
 */