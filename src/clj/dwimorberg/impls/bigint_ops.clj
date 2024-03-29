(ns dwimorberg.impls.bigint-ops
  (:require [dwimorberg.proto :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(deftype BigIntxBigIntOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigInt x y)))

(deftype BigIntxLongOps []
  ITwoArityOps
  (add [_ x y]
    (if (nil? (.bipart ^BigInt x))
      (Common/add ^long y ^long (.lpart ^BigInt x))
      (.add ^BigInteger (.bipart ^BigInt x) (BigInteger/valueOf ^long y)))))

(deftype BigIntxDoubleOps []
  ITwoArityOps
  (add [_ x y]
    (if (nil? (.bipart ^BigInt x))
      (Common/add ^double y ^long (.lpart ^BigInt x))
      (.add (BigDecimal/valueOf ^double y)
            (BigDecimal. ^BigInteger x)))))

(deftype BigIntxBigIntegerOps []
  ITwoArityOps
  (add [_ x y]
    (.add ^BigInteger y (.toBigInteger ^BigInt x))))

(deftype BigIntxRatioOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator y)
      (.multiply
       (denominator y)
       (.toBigInteger ^BigInt x)))
     (denominator y))))

(deftype BigIntxBigDecOps []
  ITwoArityOps
  (add [_ x y]
    (.add ^BigDecimal y (BigDecimal. (.toBigInteger ^BigInt x)))))
