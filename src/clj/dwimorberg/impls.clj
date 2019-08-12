(ns dwimorberg.impls
  (:require [dwimorberg.impls.long-ops :refer :all]
            [dwimorberg.impls.double-ops :refer :all]
            [dwimorberg.impls.biginteger-ops :refer :all]
            [dwimorberg.impls.bigint-ops :refer :all]
            [dwimorberg.impls.ratio-ops :refer :all]
            [dwimorberg.impls.bigdec-ops :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]
           [dwimorberg.proto IOps IFractionalSingleArityOps ISingleArityOps IWithTwo]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

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
  (abs [_ n] (Math/abs ^long n))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) *long-x-long-ops*
      (instance? Double n) *long-x-double-ops*
      (instance? BigInteger n) *long-x-biginteger-ops*
      (instance? BigInt n) *long-x-bigint-ops*
      (instance? Ratio n) *long-x-ratio-ops*
      (instance? BigDecimal n) *long-x-bigdec-ops*)))

(defrecord DoubleOps []
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
      (instance? Long n) *double-x-long-ops*
      (instance? Double n) *double-x-double-ops*
      (instance? BigInteger n) *double-x-biginteger-ops*
      (instance? BigInt n) *double-x-bigint-ops*
      (instance? Ratio n) *double-x-ratio-ops*
      (instance? BigDecimal n) *double-x-bigdec-ops*)))

(defrecord BigIntegerOps []
  ISingleArityOps
  (abs [_ n] (.abs ^BigInteger n))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) *biginteger-x-long-ops*
      (instance? Double n) *biginteger-x-double-ops*
      (instance? BigInteger n) *biginteger-x-biginteger-ops*
      (instance? BigInt n) *biginteger-x-bigint-ops*
      (instance? Ratio n) *biginteger-x-ratio-ops*
      (instance? BigDecimal n) *biginteger-x-bigdec-ops*)))

(defrecord BigIntOps []
  ISingleArityOps
  (abs [_ n]
    (if (nil? (.bipart ^BigInt n))
      (Math/abs ^long (.lpart ^BigInt n))
      (.abs ^BigInteger (.bipart ^BigInt n))))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) *bigint-x-long-ops*
      (instance? Double n) *bigint-x-double-ops*
      (instance? BigInteger n) *bigint-x-biginteger-ops*
      (instance? BigInt n) *bigint-x-bigint-ops*
      (instance? Ratio n) *bigint-x-ratio-ops*
      (instance? BigDecimal n) *bigint-x-bigdec-ops*)))

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
        :else n)))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) *ratio-x-long-ops*
      (instance? Double n) *ratio-x-double-ops*
      (instance? BigInteger n) *ratio-x-biginteger-ops*
      (instance? BigInt n) *ratio-x-bigint-ops*
      (instance? Ratio n) *ratio-x-ratio-ops*
      (instance? BigDecimal n) *ratio-x-bigdec-ops*)))

(defrecord BigDecOps []
  IFractionalSingleArityOps
  (floor [_ n]
    (.setScale ^BigDecimal n 0 RoundingMode/FLOOR))
  ISingleArityOps
  (abs [_ n] (.abs ^BigDecimal n))
  IWithTwo
  (withTwo [_ n]
    (cond
      (instance? Long n) *bigdec-x-long-ops*
      (instance? Double n) *bigdec-x-double-ops*
      (instance? BigInteger n) *bigdec-x-biginteger-ops*
      (instance? BigInt n) *bigdec-x-bigint-ops*
      (instance? Ratio n) *bigdec-x-ratio-ops*
      (instance? BigDecimal n) *bigdec-x-bigdec-ops*)))

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


(defrecord Ops []
  IOps
  (singleOps [_ n]
    (cond
      (instance? Long n) *long-ops*
      (instance? Double n) *double-ops*
      (instance? BigInteger n) *biginteger-ops*
      (instance? BigInt n) *bigint-ops*
      (instance? Ratio n) *ratio-ops*
      (instance? BigDecimal n) *bigdec-ops*))
  (fracSingleOps [_ n]
    (cond
      (instance? Double n) *double-ops*
      (instance? Ratio n) *ratio-ops*
      (instance? BigDecimal n) *bigdec-ops*
      :else *default-ops*)))


(def ^:dynamic *ops* (->Ops))
