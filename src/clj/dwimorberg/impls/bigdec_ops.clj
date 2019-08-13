(ns dwimorberg.impls.bigdec-ops
  (:require [dwimorberg.proto :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(deftype BigDecxBigDecOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal x y)))

(deftype BigDecxLongOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal x (BigDecimal/valueOf ^long y))))

(deftype BigDecxDoubleOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal x (BigDecimal/valueOf ^double y))))

(deftype BigDecxBigIntegerOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal x (BigDecimal. ^BigInteger y))))

(deftype BigDecxBigIntOps []
  ITwoArityOps
  (add [_ x y]
    (.add ^BigDecimal x (BigDecimal. (.toBigInteger ^BigInt y)))))

;; TODO: don't use max....
(deftype BigDecxRatioOps []
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
