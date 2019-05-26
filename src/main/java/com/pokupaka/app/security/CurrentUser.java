package com.pokupaka.app.security;

import com.pokupaka.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
