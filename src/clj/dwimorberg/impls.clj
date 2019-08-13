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
;; (set! *unchecked-math* :warn-on-boxed)

(def long-x-long-ops (->LongxLongOps))
(def long-x-double-ops (->LongxDoubleOps))
(def long-x-biginteger-ops (->LongxBigIntegerOps))
(def long-x-bigint-ops (->LongxBigIntOps))
(def long-x-ratio-ops (->LongxRatioOps))
(def long-x-bigdec-ops (->LongxBigDecOps))

(def double-x-double-ops (->DoublexDoubleOps))
(def double-x-long-ops (->DoublexLongOps))
(def double-x-biginteger-ops (->DoublexBigIntegerOps))
(def double-x-bigint-ops (->DoublexBigIntOps))
(def double-x-ratio-ops (->DoublexRatioOps))
(def double-x-bigdec-ops (->DoublexBigDecOps))

(def biginteger-x-biginteger-ops (->BigIntegerxBigIntegerOps))
(def biginteger-x-long-ops (->BigIntegerxLongOps))
(def biginteger-x-double-ops (->BigIntegerxDoubleOps))
(def biginteger-x-bigint-ops (->BigIntegerxBigIntOps))
(def biginteger-x-ratio-ops (->BigIntegerxRatioOps))
(def biginteger-x-bigdec-ops (->BigIntegerxBigDecOps))

(def bigint-x-bigint-ops (->BigIntxBigIntOps))
(def bigint-x-long-ops (->BigIntxLongOps))
(def bigint-x-double-ops (->BigIntxDoubleOps))
(def bigint-x-biginteger-ops (->BigIntxBigIntegerOps))
(def bigint-x-ratio-ops (->BigIntxRatioOps))
(def bigint-x-bigdec-ops (->BigIntxBigDecOps))

(def ratio-x-ratio-ops (->RatioxRatioOps))
(def ratio-x-long-ops (->RatioxLongOps))
(def ratio-x-double-ops (->RatioxDoubleOps))
(def ratio-x-biginteger-ops (->RatioxBigIntegerOps))
(def ratio-x-bigint-ops (->RatioxBigIntOps))
(def ratio-x-bigdec-ops (->RatioxBigDecOps))

(def bigdec-x-bigdec-ops (->BigDecxBigDecOps))
(def bigdec-x-long-ops (->BigDecxLongOps))
(def bigdec-x-double-ops (->BigDecxDoubleOps))
(def bigdec-x-biginteger-ops (->BigDecxBigIntegerOps))
(def bigdec-x-bigint-ops (->BigDecxBigIntOps))
(def bigdec-x-ratio-ops (->BigDecxRatioOps))


(deftype LongOps []
  ISingleArityOps
  (abs [_ n] (Math/abs ^long n))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) long-x-long-ops
      (instance? Double n) long-x-double-ops
      (instance? BigInteger n) long-x-biginteger-ops
      (instance? BigInt n) long-x-bigint-ops
      (instance? Ratio n) long-x-ratio-ops
      (instance? BigDecimal n) long-x-bigdec-ops)))

(deftype DoubleOps []
  IFractionalSingleArityOps
  (floor [_ n]
    (if (< Common/BOT_DOUBLE n Common/TOP_DOUBLE)
      (Math/floor ^double n)
      (.setScale (bigdec n) 0 RoundingMode/FLOOR)))
  ISingleArityOps
  (abs [_ n] (Math/abs ^double n))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) double-x-long-ops
      (instance? Double n) double-x-double-ops
      (instance? BigInteger n) double-x-biginteger-ops
      (instance? BigInt n) double-x-bigint-ops
      (instance? Ratio n) double-x-ratio-ops
      (instance? BigDecimal n) double-x-bigdec-ops)))

(deftype BigIntegerOps []
  ISingleArityOps
  (abs [_ n] (.abs ^BigInteger n))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) biginteger-x-long-ops
      (instance? Double n) biginteger-x-double-ops
      (instance? BigInteger n) biginteger-x-biginteger-ops
      (instance? BigInt n) biginteger-x-bigint-ops
      (instance? Ratio n) biginteger-x-ratio-ops
      (instance? BigDecimal n) biginteger-x-bigdec-ops)))

(deftype BigIntOps []
  ISingleArityOps
  (abs [_ n]
    (if (nil? (.bipart ^BigInt n))
      (Math/abs ^long (.lpart ^BigInt n))
      (.abs ^BigInteger (.bipart ^BigInt n))))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) bigint-x-long-ops
      (instance? Double n) bigint-x-double-ops
      (instance? BigInteger n) bigint-x-biginteger-ops
      (instance? BigInt n) bigint-x-bigint-ops
      (instance? Ratio n) bigint-x-ratio-ops
      (instance? BigDecimal n) bigint-x-bigdec-ops)))

(deftype RatioOps []
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
        :else n)))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) ratio-x-long-ops
      (instance? Double n) ratio-x-double-ops
      (instance? BigInteger n) ratio-x-biginteger-ops
      (instance? BigInt n) ratio-x-bigint-ops
      (instance? Ratio n) ratio-x-ratio-ops
      (instance? BigDecimal n) ratio-x-bigdec-ops)))

(deftype BigDecOps []
  IFractionalSingleArityOps
  (floor [_ n]
    (.setScale ^BigDecimal n 0 RoundingMode/FLOOR))
  ISingleArityOps
  (abs [_ n] (.abs ^BigDecimal n))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) bigdec-x-long-ops
      (instance? Double n) bigdec-x-double-ops
      (instance? BigInteger n) bigdec-x-biginteger-ops
      (instance? BigInt n) bigdec-x-bigint-ops
      (instance? Ratio n) bigdec-x-ratio-ops
      (instance? BigDecimal n) bigdec-x-bigdec-ops)))

(deftype DefaultOps []
  IFractionalSingleArityOps
  (floor [_ n] n))


(def long-ops (->LongOps))
(def double-ops (->DoubleOps))
(def biginteger-ops (->BigIntegerOps))
(def bigint-ops (->BigIntOps))
(def ratio-ops (->RatioOps))
(def bigdec-ops (->BigDecOps))
(def default-ops (->DefaultOps))


(deftype Ops []
  IOps
  (singleOps [_ n]
    (cond
      (instance? Long n) long-ops
      (instance? Double n) double-ops
      (instance? BigInteger n) biginteger-ops
      (instance? BigInt n) bigint-ops
      (instance? Ratio n) ratio-ops
      (instance? BigDecimal n) bigdec-ops))
  (fracSingleOps [_ n]
    (cond
      (instance? Double n) double-ops
      (instance? Ratio n) ratio-ops
      (instance? BigDecimal n) bigdec-ops
      :else default-ops)))


(def ops (->Ops))
