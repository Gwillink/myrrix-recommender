/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.myrrix.common.stats;

import java.io.Serializable;

import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;

/**
 * <p>Ported from Mahout {@code WeightedRunningAverage}, to use Commons Math3 framework.</p>
 *
 * <p>This class is not thread-safe.</p>
 *
 * @author Sean Owen
 * @author Mahout
 * @since 1.0
 */
public final class DoubleWeightedMean extends AbstractStorelessUnivariateStatistic implements Serializable {
  
  private double totalWeight;
  private double mean;
  
  public DoubleWeightedMean() {
    this(0.0, Double.NaN);
  }
  
  private DoubleWeightedMean(double totalWeight, double mean) {
    this.totalWeight = totalWeight;
    this.mean = mean;
  }

  @Override
  public DoubleWeightedMean copy() {
    return new DoubleWeightedMean(totalWeight, mean);
  }

  @Override
  public void clear() {
    totalWeight = 0.0;
    mean = Double.NaN;
  }

  @Override
  public double getResult() {
    return mean;
  }

  @Override
  public long getN() {
    return (long) totalWeight;
  }

  @Override
  public void increment(double datum) {
    increment(datum, 1.0);
  }
  
  public void increment(double datum, double weight) {
    double oldTotalWeight = totalWeight;
    totalWeight += weight;
    if (oldTotalWeight <= 0) {
    	mean = datum;
    } else {
      mean = mean * oldTotalWeight / totalWeight + datum * weight / totalWeight;
    }
  }
  
  public void decrement(double datum) {
    decrement(datum, 1);
  }
  
  public void decrement(double datum, double weight) {
    double oldTotalWeight = totalWeight;
    totalWeight -= weight;
    if (totalWeight <= 0.0) {
      clear();
    } else {
      mean = mean * oldTotalWeight / totalWeight - datum * weight / totalWeight;
    }
  }
  
  @Override
  public String toString() {
    return Double.toString(mean);
  }
  
}
