package com.spotify.annoy.jni.base;

import java.util.List;

/**
 * Annoy interface
 * Modeled after: https://github.com/spotify/annoy/blob/master/annoy/__init__.py, sorta
 */
public interface AnnoyIndex {

  List<Integer> getNearestByVector(List<Float> vector, int n);

  List<Integer> getNearestByVector(List<Float> vector, int n, int searchK);

  List<Integer> getNearestByItem(int item, int n);

  List<Integer> getNearestByItem(int item, int n, int searchK);

  List<Float> getItemVector(int i);

  float getDistance(int i, int j);

  int size();

  AnnoyIndex save(String filename);
}
