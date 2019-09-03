package cn.songs.args;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FlagsTest {
    @Rule
    public ExpectedException expectedE = ExpectedException.none();

    @Test
    public void should_throw_e_if_set_undefined_flag() {
        Flags<Boolean> flags = new Flags<>();
        expectedE.expect(IllegalArgumentException.class);
        expectedE.expectMessage("setting an undefined flag: undefined");
        flags.setFlag("undefined", true);
    }

    @Test
    public void should_throw_e_if_get_undefined_flag() {
        Flags<Boolean> flags = new Flags<>();
        expectedE.expect(IllegalArgumentException.class);
        expectedE.expectMessage("getting an undefined flag: undefined");
        flags.getFlag("undefined");
    }

    @Test
    public void should_have_bool_flags() {
        Flags<Boolean> flags = new Flags<>();
        flags.addFlag("aBoolFlagWithDefaultFalse", false);
        assertThat(flags.getFlag("aBoolFlagWithDefaultFalse"), is(false));
        flags.setFlag("aBoolFlagWithDefaultFalse", true);
        assertThat(flags.getFlag("aBoolFlagWithDefaultFalse"), is(true));
    }

    @Test
    public void should_have_int_flags() {
        Flags<Integer> flags = new Flags<>();
        flags.addFlag("anIntFlagWithDefault0", 0);
        assertThat(flags.getFlag("anIntFlagWithDefault0"), is(0));
        flags.setFlag("anIntFlagWithDefault0", 10);
        assertThat(flags.getFlag("anIntFlagWithDefault0"), is(10));
    }
}
