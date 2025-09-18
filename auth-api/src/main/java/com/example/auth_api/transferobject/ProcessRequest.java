package com.example.auth_api.transferobject;

import jakarta.validation.constraints.NotBlank;

public record ProcessRequest(@NotBlank String text) {}

