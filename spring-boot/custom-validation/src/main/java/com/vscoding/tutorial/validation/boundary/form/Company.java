package com.vscoding.tutorial.validation.boundary.form;

import com.vscoding.tutorial.validation.boundary.validation.ValidatedCompanyId;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Company data
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
  @NotBlank
  @ValidatedCompanyId
  String id;
}
