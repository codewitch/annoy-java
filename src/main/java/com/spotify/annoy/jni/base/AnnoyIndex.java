/*
 * -\-\-
 * annoy-java
 * --
 * Copyright (C) 2017 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.annoy.jni.base;

import java.io.Closeable;
import java.util.List;

/**
 * Annoy interface
 * Modeled after: https://github.com/spotify/annoy/blob/master/annoy/__init__.py, sorta
 */
public interface AnnoyIndex extends Closeable {

  List<Integer> getNearestByVector(List<Float> vector, int nbNeighbors);

  List<Integer> getNearestByVector(List<Float> vector, int nbNeighbors, int searchK);

  List<Integer> getNearestByItem(int item, int nbNeighbors);

  List<Integer> getNearestByItem(int item, int nbNeighbors, int searchK);

  List<Float> getItemVector(int item);

  float getDistance(int itemA, int itemB);

  int size();

  void close();

  AnnoyIndex save(String filename);
}
