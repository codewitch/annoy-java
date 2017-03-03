package com.spotify.annoy;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

class AnnoyIndexImpl implements AnnoyIndex {

  private final int dim;

  public List<Integer> getNearestByVector(List<Float> vector, int n) {
    validateVecSize(vector);
    return primitiveToBoxed(cppGetNearestByVector(boxedToPrimitive(vector), n));
  }

  public List<Integer> getNearestByVectorK(List<Float> vector, int n, int searchK) {
    validateVecSize(vector);
    return primitiveToBoxed(cppGetNearestByVectorK(boxedToPrimitive(vector), n, searchK));
  }

  public List<Integer> getNearestByItem(int item, int n) {
    return primitiveToBoxed(cppGetNearestByItem(item, n));
  }

  public List<Integer> getNearestByItemK(int item, int n, int searchK) {
    return primitiveToBoxed(cppGetNearestByItemK(item, n, searchK));
  }

  public AnnoyIndex save(String filename) {
    cppSave(filename);
    return this;
  }

  public List<Float> getItemVector(int i) {
    return primitiveToBoxed(cppGetItemVector(i));
  }

  public float getDistance(int i, int j) {
    return cppGetDistance(i, j);
  }

  public int size() {
    return cppSize();
  }

  // Construction

  AnnoyIndexImpl(int dim) {
    this.dim = dim;
    final String dir = System.getProperty("java.library.path");
    //set explicit path for our custom library
    System.load(dir + "/libannoy.jnilib"); //TODO: linux name is different.
    cppCtor(dim);
  }

  AnnoyIndex addItem(int i, List<Float> vector) {
    validateVecSize(vector);
    cppAddItem(i, boxedToPrimitive(vector));
    return this;
  }

  AnnoyIndex addAllItems(Iterable<List<Float>> vectors) {
    int i = size();
    for (List<Float> vector : vectors) {
      addItem(i++, vector);
    }
    return this;
  }

  AnnoyIndex build(int nTrees) {
    cppBuild(nTrees);
    return this;
  }

  AnnoyIndex load(String filename) {
    cppLoad(filename);
    return this;
  }

  AnnoyIndex setSeed(int seed) {
    cppSetSeed(seed);
    return this;
  }

  // Helpers

  private static List<Float> primitiveToBoxed(float[] v) {
    return Arrays.asList(ArrayUtils.toObject(v));
  }

  private static List<Integer> primitiveToBoxed(int[] v) {
    return Arrays.asList(ArrayUtils.toObject(v));
  }

  private static float[] boxedToPrimitive(List<Float> v) {
    return ArrayUtils.toPrimitive(v.toArray(new Float[0]));
  }

  private void validateVecSize(List<Float> vector) {
    if (vector.size() != dim) {
      throw new RuntimeException("Item's vector should match the dimension of the tree");
    }
  }

  // Native cpp  methods

  private native void cppCtor(int f);

  private native void cppAddItem(int i, float[] vector);

  private native int[] cppGetNearestByVector(float[] vector, int n);

  private native int[] cppGetNearestByVectorK(float[] vector, int n, int searchK);

  private native int[] cppGetNearestByItem(int item, int n);

  private native int[] cppGetNearestByItemK(int item, int n, int searchK);

  private native void cppBuild(int nTrees);

  private native void cppSave(String filename);

  private native void cppLoad(String filename);

  private native float[] cppGetItemVector(int i);

  private native float cppGetDistance(int i, int j);

  private native int cppSize();

  private native void cppSetSeed(int seed);
}
