(ns dwimorberg.impls
  (:require [dwimorberg.proto :refer :all]
            [dwimorberg.impls.long-ops :refer :all]
            [dwimorberg.impls.double-ops :refer :all]
            [dwimorberg.impls.biginteger-ops :refer :all]
            [dwimorberg.impls.bigint-ops :refer :all]
            [dwimorberg.impls.ratio-ops :refer :all]
            [dwimorberg.impls.bigdec-ops :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)

(def ^:dynamic *long-x-long-ops* (->LongxLongOps))
(def ^:dynamic *long-x-double-ops* (->LongxDoubleOps))
(def ^:dynamic *long-x-biginteger-ops* (->LongxBigIntegerOps))
(def ^:dynamic *long-x-bigint-ops* (->LongxBigIntOps))
(def ^:dynamic *long-x-ratio-ops* (->LongxRatioOps))
(def ^:dynamic *long-x-bigdec-ops* (->LongxBigDecOps))

(def ^:dynamic *double-x-double-ops* (->DoublexDoubleOps))
(def ^:dynamic *double-x-long-ops* (->DoublexLongOps))
(def ^:dynamic *double-x-biginteger-ops* (->DoublexBigIntegerOps))
(def ^:dynamic *double-x-bigint-ops* (->DoublexBigIntOps))
(def ^:dynamic *double-x-ratio-ops* (->DoublexRatioOps))
(def ^:dynamic *double-x-bigdec-ops* (->DoublexBigDecOps))

(def ^:dynamic *biginteger-x-biginteger-ops* (->BigIntegerxBigIntegerOps))
(def ^:dynamic *biginteger-x-long-ops* (->BigIntegerxLongOps))
(def ^:dynamic *biginteger-x-double-ops* (->BigIntegerxDoubleOps))
(def ^:dynamic *biginteger-x-bigint-ops* (->BigIntegerxBigIntOps))
(def ^:dynamic *biginteger-x-ratio-ops* (->BigIntegerxRatioOps))
(def ^:dynamic *biginteger-x-bigdec-ops* (->BigIntegerxBigDecOps))

(def ^:dynamic *bigint-x-bigint-ops* (->BigIntxBigIntOps))
(def ^:dynamic *bigint-x-long-ops* (->BigIntxLongOps))
(def ^:dynamic *bigint-x-double-ops* (->BigIntxDoubleOps))
(def ^:dynamic *bigint-x-biginteger-ops* (->BigIntxBigIntegerOps))
(def ^:dynamic *bigint-x-ratio-ops* (->BigIntxRatioOps))
(def ^:dynamic *bigint-x-bigdec-ops* (->BigIntxBigDecOps))

(def ^:dynamic *ratio-x-ratio-ops* (->RatioxRatioOps))
(def ^:dynamic *ratio-x-long-ops* (->RatioxLongOps))
(def ^:dynamic *ratio-x-double-ops* (->RatioxDoubleOps))
(def ^:dynamic *ratio-x-biginteger-ops* (->RatioxBigIntegerOps))
(def ^:dynamic *ratio-x-bigint-ops* (->RatioxBigIntOps))
(def ^:dynamic *ratio-x-bigdec-ops* (->RatioxBigDecOps))

(def ^:dynamic *bigdec-x-bigdec-ops* (->BigDecxBigDecOps))
(def ^:dynamic *bigdec-x-long-ops* (->BigDecxLongOps))
(def ^:dynamic *bigdec-x-double-ops* (->BigDecxDoubleOps))
(def ^:dynamic *bigdec-x-biginteger-ops* (->BigDecxBigIntegerOps))
(def ^:dynamic *bigdec-x-bigint-ops* (->BigDecxBigIntOps))
(def ^:dynamic *bigdec-x-ratio-ops* (->BigDecxRatioOps))


(defrecord LongOps []
  ISingleArityOps
  (abs [_ n] (Math/abs ^long n)))

(defrecord DoubleOps []
  IFractionalSingleArityOps
  (floor [_ n]
    (if (< Common/BOT_DOUBLE n Common/TOP_DOUBLE)
      (Math/floor ^double n)
      (.setScale (bigdec n) 0 RoundingMode/FLOOR)))
  ISingleArityOps
  (abs [_ n] (Math/abs ^double n)))

(defrecord BigIntegerOps []
  ISingleArityOps
  (abs [_ n] (.abs ^BigInteger n)))

(defrecord BigIntOps []
  ISingleArityOps
  (abs [_ n]
    (if (nil? (.bipart ^BigInt n))
      (Math/abs ^long (.lpart ^BigInt n))
      (.abs ^BigInteger (.bipart ^BigInt n)))))

(defrecord RatioOps []
  IFractionalSingleArityOps
  (floor [_ n]
    (let [numer (numerator n)
          denom (denominator n)]
      (if (< Common/BOT_DOUBLE n Common/TOP_DOUBLE)
        (Math/floor (double n))
        (.setScale
         (.divide
          (bigdec numer)
          (bigdec denom)
          16 RoundingMode/HALF_UP)
         0 RoundingMode/FLOOR))))
  ISingleArityOps
  (abs [_ n]
    (let [numer (numerator n)
          denom (denominator n)]
      (cond
        (and (neg? numer) (pos? denom)) (/ (.negate numer) denom)
        (and (pos? numer) (neg? denom)) (/ numer (.negate denom))
        :else n))))

(defrecord BigDecOps []
  IFractionalSingleArityOps
  (floor [_ n]
    (.setScale ^BigDecimal n 0 RoundingMode/FLOOR))
  ISingleArityOps
  (abs [_ n] (.abs ^BigDecimal n)))

(defrecord DefaultOps []
  IFractionalSingleArityOps
  (floor [_ n] n))


(def ^:dynamic *long-ops* (->LongOps))
(def ^:dynamic *double-ops* (->DoubleOps))
(def ^:dynamic *biginteger-ops* (->BigIntegerOps))
(def ^:dynamic *bigint-ops* (->BigIntOps))
(def ^:dynamic *ratio-ops* (->RatioOps))
(def ^:dynamic *bigdec-ops* (->BigDecOps))
(def ^:dynamic *default-ops* (->DefaultOps))


(extend-protocol IDispatch
  Long
  (singleOps [_] *long-ops*)
  (fracSingleOps [_] *default-ops*)
  (dispatchType [_] 'Long)
  Double
  (singleOps [_] *double-ops*)
  (fracSingleOps [_] *double-ops*)
  (dispatchType [_] 'Double)
  BigInteger
  (singleOps [_] *biginteger-ops*)
  (fracSingleOps [_] *default-ops*)
  (dispatchType [_] 'BigInteger)
  BigInt
  (singleOps [_] *bigint-ops*)
  (fracSingleOps [_] *default-ops*)
  (dispatchType [_] 'BigInt)
  Ratio
  (singleOps [_] *ratio-ops*)
  (fracSingleOps [_] *ratio-ops*)
  (dispatchType [_] 'Ratio)
  BigDecimal
  (singleOps [_] *bigdec-ops*)
  (fracSingleOps [_] *bigdec-ops*)
  (dispatchType [_] 'BigDec))

(extend-protocol IWithTwo
  Long
  (withTwo [_ n]
    (let [t (dispatchType n) ]
      (case t
        Long *long-x-long-ops*
        Double *long-x-double-ops*
        BigInteger *long-x-biginteger-ops*
        BigInt *long-x-bigint-ops*
        Ratio *long-x-ratio-ops*
        BigDec *long-x-bigdec-ops*)))
  Double
  (withTwo [_ n]
    (let [t (dispatchType n)]
      (case t
        Long *double-x-long-ops*
        Double *double-x-double-ops*
        BigInteger *double-x-biginteger-ops*
        BigInt *double-x-bigint-ops*
        Ratio *double-x-ratio-ops*
        BigDec *double-x-bigdec-ops*)))
  BigInteger
  (withTwo [_ n]
    (let [t (dispatchType n)]
      (case t
        Long *biginteger-x-long-ops*
        Double *biginteger-x-double-ops*
        BigInteger *biginteger-x-biginteger-ops*
        BigInt *biginteger-x-bigint-ops*
        Ratio *biginteger-x-ratio-ops*
        BigDec *biginteger-x-bigdec-ops*)))
  BigInt
  (withTwo [_ n]
    (let [t (dispatchType n)]
      (case t
        Long *bigint-x-long-ops*
        Double *bigint-x-double-ops*
        BigInteger *bigint-x-biginteger-ops*
        BigInt *bigint-x-bigint-ops*
        Ratio *bigint-x-ratio-ops*
        BigDec *bigint-x-bigdec-ops*)))
  Ratio
  (withTwo [_ n]
    (let [t (dispatchType n)]
      (case t
        Long *ratio-x-long-ops*
        Double *ratio-x-double-ops*
        BigInteger *ratio-x-biginteger-ops*
        BigInt *ratio-x-bigint-ops*
        Ratio *ratio-x-ratio-ops*
        BigDec *ratio-x-bigdec-ops*)))
  BigDecimal
  (withTwo [_ n]
    (let [t (dispatchType n)]
      (case t
        Long *bigdec-x-long-ops*
        Double *bigdec-x-double-ops*
        BigInteger *bigdec-x-biginteger-ops*
        BigInt *bigdec-x-bigint-ops*
        Ratio *bigdec-x-ratio-ops*
        BigDec *bigdec-x-bigdec-ops*))))
