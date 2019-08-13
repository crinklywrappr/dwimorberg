(ns dwimorberg.impls.long-ops
  (:require [dwimorberg.proto :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(deftype LongxLongOps []
  ITwoArityOps
  (add [_ x y] (Common/add ^long x ^long y)))

(deftype LongxDoubleOps []
  ITwoArityOps
  (add [_ x y] (Common/add ^double y ^long x)))

(deftype LongxBigIntegerOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigInteger y (biginteger x))))

(deftype LongxBigIntOps []
  ITwoArityOps
  (add [_ x y]
    (if (nil? (.bipart ^BigInt y))
      (Common/add ^long x ^long (.lpart ^BigInt y))
      (.add ^BigInteger (.bipart ^BigInt y) (BigInteger/valueOf ^long x)))))

(deftype LongxRatioOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator y)
      (.multiply
       (denominator y)
       (BigInteger/valueOf x)))
     (denominator y))))

(deftype LongxBigDecOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal y (BigDecimal/valueOf ^long x))))


