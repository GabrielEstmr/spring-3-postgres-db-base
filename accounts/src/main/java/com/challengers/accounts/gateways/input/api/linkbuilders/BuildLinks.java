package com.challengers.accounts.gateways.input.api.linkbuilders;

import org.springframework.hateoas.Link;

import java.util.List;

public interface BuildLinks<T> {
  List<Link> build(T object);
}
