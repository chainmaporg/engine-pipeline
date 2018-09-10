package com.afedulov.search;

import lombok.Getter;

/**
 * Created on 15.07.2015 by afedulov
 */
@Getter
public class SearchHit {
  private final String url;

  public SearchHit(String url){
    this.url = url;
  }

public String getUrl() {
	// TODO Auto-generated method stub
	return url;
}

}
