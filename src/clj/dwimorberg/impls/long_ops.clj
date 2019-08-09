(ns dwimorberg.impls.long-ops
  (:require [dwimorberg.proto :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)

(defrecord LongxLongOps []
  ITwoArityOps
  (add [_ x y] (Common/add ^long x ^long y)))

(defrecord LongxDoubleOps []
  ITwoArityOps
  (add [_ x y] (Common/add ^double y ^long x)))

(defrecord LongxBigIntegerOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigInteger y (biginteger x))))

(defrecord LongxBigIntOps []
  ITwoArityOps
  (add [_ x y]
    (if (nil? (.bipart ^BigInt y))
      (Common/add ^long x ^long (.lpart ^BigInt y))
      (.add ^BigInteger (.bipart ^BigInt y) (BigInteger/valueOf ^long x)))))

(defrecord LongxRatioOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator y)
      (.multiply
       (denominator y)
       (BigInteger/valueOf x)))
     (denominator y))))

(defrecord LongxBigDecOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal y (BigDecimal/valueOf ^long x))))


