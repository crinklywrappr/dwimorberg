(ns dwimorberg.impls.biginteger-ops
  (:require [dwimorberg.proto :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(deftype BigIntegerxBigIntegerOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigInteger x y)))

;; TODO: return long if possible?
(deftype BigIntegerxLongOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigInteger x (biginteger y))))

(deftype BigIntegerxDoubleOps []
  ITwoArityOps
  (add [_ x y]
    (.add (BigDecimal/valueOf ^double y)
          (BigDecimal. ^BigInteger x))))

(deftype BigIntegerxBigIntOps []
  ITwoArityOps
  (add [_ x y]
    (.add ^BigInteger x (.toBigInteger ^BigInt y))))

(deftype BigIntegerxRatioOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator y)
      (.multiply (denominator y) x))
     (denominator y))))

(deftype BigIntegerxBigDecOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal y (BigDecimal. ^BigInteger x))))
