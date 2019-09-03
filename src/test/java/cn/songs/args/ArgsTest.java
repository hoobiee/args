package cn.songs.args;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArgsTest {
    @Rule
    public ExpectedException expectedE = ExpectedException.none();

    @Test
    public void should_throw_e_if_undefined() {
        Args arg = new Args();
        String[] args = new String[] {"-undefined"};
        expectedE.expect(IllegalArgumentException.class);
        expectedE.expectMessage("undefined flag: undefined");
        arg.parse(args);
    }

    @Test
    public void should_throw_e_if_mismatch_type() {
        Args arg = new Args();
        arg.setBool("aBool", false);
        String[] args = new String[] {"-aBool"};
        expectedE.expect(IllegalArgumentException.class);
        expectedE.expectMessage("flag type mismatch: aBool");
        arg.parse(args);
        arg.getInt("aBool");
    }

    @Test
    public void should_throw_e_if_arg_without_flag() {
        Args arg = new Args();
        arg.setBool("aBool", false);
        String[] args = new String[] {"-aBool", "true"};
        expectedE.expect(IllegalArgumentException.class);
        expectedE.expectMessage("arg without flag: true");
        arg.parse(args);
    }

    @Test
    public void should_match_bool_flag() {
        Args arg = new Args();
        arg.setBool("notInArgs", false);
        arg.setBool("inArgs", false);
        String[] args = new String[] {"-inArgs"};
        arg.parse(args);
        assertThat(arg.getBool("notInArgs"), is(false));
        assertThat(arg.getBool("inArgs"), is(true));
    }

    @Test
    public void should_match_int_flag() {
        Args arg = new Args();
        arg.setInt("notInArgs", 0);
        arg.setInt("inArgs", 0);
        String[] args = new String[] {"-inArgs", "10"};
        arg.parse(args);
        assertThat(arg.getInt("notInArgs"), is(0));
        assertThat(arg.getInt("inArgs"), is(10));
    }

    @Test
    public void should_match_string_flag() {
        Args arg = new Args();
        arg.setString("notInArgs", "");
        arg.setString("inArgs", "");
        String[] args = new String[] {"-inArgs", "aString"};
        arg.parse(args);
        assertThat(arg.getString("notInArgs"), is(""));
        assertThat(arg.getString("inArgs"), is("aString"));
    }

    @Test
    public void should_match_strings_flag() {
        Args arg = new Args();
        arg.setStrings("notInArgs", new String[] {});
        arg.setStrings("inArgs", new String[] {});
        String[] args = new String[] {"-inArgs", "aString", "anotherString"};
        arg.parse(args);
        assertThat(arg.getStrings("notInArgs").length, is(0));
        assertThat(arg.getStrings("inArgs").length, is(2));
        assertThat(arg.getStrings("inArgs")[0], is("aString"));
        assertThat(arg.getStrings("inArgs")[1], is("anotherString"));
    }

    @Test
    public void should_match_multiple_args() {
        Args arg = new Args();
        arg.setBool("aBool", false);
        arg.setInt("anInt", 0);
        arg.setString("aString", "");
        arg.setStrings("someStrings", new String[] {});
        String[] args = new String[] {"-anInt", "92", "-someStrings", "some", "strings", "-aBool"};
        arg.parse(args);
        assertThat(arg.getBool("aBool"), is(true));
        assertThat(arg.getInt("anInt"), is(92));
        assertThat(arg.getString("aString"), is(""));
        assertThat(arg.getStrings("someStrings").length, is(2));
        assertThat(arg.getStrings("someStrings")[0], is("some"));
        assertThat(arg.getStrings("someStrings")[1], is("strings"));
    }
}
