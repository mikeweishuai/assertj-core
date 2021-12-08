package org.assertj.core.api.objectarray;

import org.assertj.core.api.ObjectArrayAssert;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;

import org.assertj.core.api.StringAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link ObjectArrayAssert#singleElement()}</code>.
 *
 * @author Shuai Wei
 */
class ObjectArrayAssert_singleElement_Test {
  @Test
  void should_fail_if_array_is_empty() {
    // GIVEN
    String[] array = new String[]{};
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(array).singleElement());
    // THEN
    then(assertionError).hasMessageContaining("Expecting actual not to be empty");
  }

  @Test
  void should_pass_if_array_contains_only_one_element() {
    // GIVEN
    String[] array = new String[]{"Manchester"};
    // WHEN/THEN
    assertThat(array).singleElement();
  }

  @Test
  void should_fail_if_array_contains_more_than_one_elements() {
    // GIVEN
    String[] array = new String[]{"Manchester", "Liverpool"};
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(array).singleElement());
    // THEN
    then(assertionError).hasMessageContaining("Expected size: 1 but was: 2");
  }

  @Test
  void should_pass_if_following_assertion_on_first_element_is_true() {
    // GIVEN
    Class<StringAssert> assertClass = StringAssert.class;
    String[] array = new String[]{"Manchester"};
    // WHEN/THEN
    assertThat(array, assertClass).singleElement().startsWith("Man");
  }

  @Test
  void should_fail_if_following_assertion_on_first_element_is_false() {
    // GIVEN
    Class<StringAssert> assertClass = StringAssert.class;
    String[] array = new String[]{"Liverpool"};
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(array, assertClass).singleElement().startsWith("Man"));
    // THEN
    then(assertionError).hasMessageContaining("to start with:");
  }
}
