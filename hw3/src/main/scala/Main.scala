
package ru.linreg


import breeze.linalg.{*, DenseMatrix, DenseVector, csvread, csvwrite, inv, norm, sum}
import breeze.stats.mean

import java.io.File


class LinearRegression() {
  private var W: DenseVector[Double] = DenseVector()

  private var X_dev: DenseMatrix[Double] = DenseMatrix(1,1,0.0)
  private var Y_dev: DenseVector[Double] = DenseVector()
  private var X_valid: DenseMatrix[Double]= DenseMatrix(1,1,0.0)
  private var Y_valid: DenseVector[Double] = DenseVector()

  def SplitData(X : DenseMatrix[Double],
                 Y: DenseVector[Double],
                N: Integer = 100) : Double ={
    //The number of observations (cases/ instances):
    //val N = 100
    val max_index_train = N/2 - 1
    val start_index_validation = max_index_train + 1

    this.X_dev= X(0 to  max_index_train, ::)
    this.X_valid = X(start_index_validation to (N - 1), ::)
    this.Y_dev = Y(0 to max_index_train)
    this.Y_valid = Y(start_index_validation to (N - 1))
    0
  }

  def Fit(XX: DenseMatrix[Double],
          YY: DenseVector[Double])  : Double = {

    this.SplitData(XX,YY)

    var X = this.X_dev
    var Y = this.Y_dev
    this.W = inv(X.t * X) * X.t * Y

    X = this.X_valid
    Y = this.Y_valid
    val YPred = X.toDenseMatrix * this.W
    val YTrue = Y.toDenseVector
    val Score_valid = this.calcR2(YTrue, YPred)
    Score_valid
  }

  def Predict(X: DenseMatrix[Double]): DenseVector[Double] = {
    X(::, *).map(Col => Col  ) * this.W
  }

  def calcR2(YTrue: DenseVector[Double],
                   YPred: DenseVector[Double]): Double = {
    val MeanT = mean(YTrue)
    val Total = sum((YTrue - MeanT) * (YTrue - MeanT))
    1 - sum((YTrue - YPred) * (YTrue - YPred)) / Total
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val TrainF = "../data/train.csv" //  args(0)
    val TestF = "../data/test.csv" //args(1)
    val PredF = "../data/pred.csv" //args(2)
    var Train = csvread(new File(TrainF), ';', skipLines = 1)
    var Test = csvread(new File(TestF), ';', skipLines = 1)
    val LinearRegression = new LinearRegression()
    val Score_valid = LinearRegression.Fit(XX = Train(::, 1 to -1), YY = Train(::, 0))
    val Predicts = LinearRegression.Predict(X = Test(::, 1 to -1))
    csvwrite(new File(PredF), Predicts.asDenseMatrix.t)
    println("R2 on validation :")
    println(Score_valid)
  }
}

