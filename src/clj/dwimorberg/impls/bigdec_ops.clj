(ns dwimorberg.impls.bigdec-ops
  (:require [dwimorberg.proto :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(defrecord BigDecxBigDecOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal x y)))

(defrecord BigDecxLongOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal x (BigDecimal/valueOf ^long y))))

(defrecord BigDecxDoubleOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal x (BigDecimal/valueOf ^double y))))

(defrecord BigDecxBigIntegerOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal x (BigDecimal. ^BigInteger y))))

(defrecord BigDecxBigIntOps []
  ITwoArityOps
  (add [_ x y]
    (.add ^BigDecimal x (BigDecimal. (.toBigInteger ^BigInt y)))))

;; TODO: don't use max....
(defrecord BigDecxRatioOps []
  ITwoArityOps
  (add [_ x y]
    (if (Common/isInt ^BigDecimal x)
      (Ratio.
       (.add
        (numerator y)
        (.multiply
         (denominator y)
         (.toBigInteger ^BigDecimal x)))
       (denominator x))
      (let [numer (BigDecimal. (numerator y))
            denom (BigDecimal. (denominator y))]
        (.add
         (.divide numer denom
                  (max (.precision ^BigDecimal x) 32)
                  RoundingMode/HALF_UP)
         x)))))
