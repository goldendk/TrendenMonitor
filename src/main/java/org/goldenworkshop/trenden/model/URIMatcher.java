package org.goldenworkshop.trenden.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class URIMatcher {
    private List<Pattern> patterns = new ArrayList<>();

    public void add(String commaSeparated){
        String[] split = StringUtils.split(commaSeparated, ",");

        for(String s : split){
            patterns.add(Pattern.compile(s));
        }
    }


    public boolean matches(String uri){
        for(Pattern p : patterns){
            if(p.matcher(uri).matches()){
                return true;
            }
        }
        return false;

    }

}
