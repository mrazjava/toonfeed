package com.mrazjava.toonfeed;

import java.util.List;

/**
 * Generic contract for providing cartoon data. Sources may vary.
 * 
 * @author mrazjava
 */
public interface ToonProvider {

    List<ToonModel> getToons();
}
