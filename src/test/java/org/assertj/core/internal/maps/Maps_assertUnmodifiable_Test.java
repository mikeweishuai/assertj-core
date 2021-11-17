/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2021 the original author or authors.
 */
package org.assertj.core.internal.maps;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.MapsBaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldBeUnmodifiable.shouldBeUnmodifiable;
import static org.assertj.core.util.FailureMessages.actualIsNull;


public class Maps_assertUnmodifiable_Test extends MapsBaseTest {

  @Test
  void should_fail_if_map_is_null() {
    // GIVEN
    Map<?, ?> actual = null;
    AssertionInfo info = someInfo();
    // WHEN
    Throwable error = catchThrowable(() -> maps.assertUnmodifiable(info, actual));
    // THEN
    assertThat(error).isInstanceOf(AssertionError.class);
    then(error).hasMessage(actualIsNull());
  }

  @ParameterizedTest
  @MethodSource("unmodifiableMaps")
  void should_pass(Map<?, ?> actual) {
    // WHEN/THEN
    assertThatNoException().as(actual.getClass().getName())
      .isThrownBy(() -> maps.assertUnmodifiable(someInfo(), actual));
  }

  private static Stream<Map<?, ?>> unmodifiableMaps() {
    return Stream.of(Collections.unmodifiableMap(new HashMap<>()),
      Collections.unmodifiableNavigableMap(new TreeMap<>()),
      Collections.unmodifiableSortedMap(new TreeMap<>())
    );
  }

  @ParameterizedTest
  @MethodSource("modifiableMaps")
  void should_fail(Map<?, ?> actual) {
    // WHEN
    Throwable error = catchThrowable(() -> maps.assertUnmodifiable(someInfo(), actual));
    // THEN
    assertThat(error).isInstanceOf(AssertionError.class);
    then(error).hasMessage(shouldBeUnmodifiable("Map.clear()").create());
  }

  private static Stream<Map<?, ?>> modifiableMaps() {
    return Stream.of(new HashMap<>(),
      new TreeMap<>()
    );
  }
}
