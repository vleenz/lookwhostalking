import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class UnitTests {

    @Test
    public void sentenceSplitWithExcl() {
        App.sentenceSplit("we 3 (excl) went");
        assertThat(App.sentence.verb, equalTo("haere"));
        assertThat(App.sentence.pronoun, equalTo("mātou"));
        assertThat(App.sentence.tense, equalTo("I"));
        assertThat(App.tenseCheck, is(true));
        assertThat(App.verbCheck, is(true));
        assertThat(App.pronounCheck, is(true));
    }

    @Test
    public void sentenceSplitWithIncl() {
        App.sentenceSplit("we 3 (incl) made");
        assertThat(App.sentence.verb, equalTo("hanga"));
        assertThat(App.sentence.pronoun, equalTo("tātou"));
        assertThat(App.sentence.tense, equalTo("I"));
        assertThat(App.tenseCheck, is(true));
        assertThat(App.verbCheck, is(true));
        assertThat(App.pronounCheck, is(true));
    }

    @Test
    public void sentenceSplitWithoutInclExcl() {
        App.sentenceSplit("I read");
        assertThat(App.sentence.verb, equalTo("pānui"));
        assertThat(App.sentence.pronoun, equalTo("au"));
        assertThat(App.sentence.tense, equalTo("I"));
        assertThat(App.tenseCheck, is(true));
        assertThat(App.verbCheck, is(true));
        assertThat(App.pronounCheck, is(true));
    }

    @Test
    public void sentenceSplitWithTheyIncl() {
        App.sentenceSplit("They (3 incl) saw");
        assertThat(App.sentence.verb, equalTo(""));
        assertThat(App.sentence.pronoun, equalTo(""));
        assertThat(App.sentence.tense, equalTo(""));
        assertThat(App.tenseCheck, is(false));
        assertThat(App.verbCheck, is(false));
        assertThat(App.pronounCheck, is(false));
    }

    @Test
    public void sentenceSplitWithNonNumericAmount() {
        App.sentenceSplit("he saw");
        assertThat(App.sentence.verb, equalTo("kite"));
        assertThat(App.sentence.pronoun, equalTo("ia"));
        assertThat(App.sentence.tense, equalTo("I"));
        assertThat(App.tenseCheck, is(true));
        assertThat(App.verbCheck, is(true));
        assertThat(App.pronounCheck, is(true));
    }
}
