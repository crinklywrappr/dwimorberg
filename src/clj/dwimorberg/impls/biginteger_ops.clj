(ns dwimorberg.impls.biginteger-ops
  (:require [dwimorberg.proto :refer :all])
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(defrecord BigIntegerxBigIntegerOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigInteger x y)))

;; TODO: return long if possible?
(defrecord BigIntegerxLongOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigInteger x (biginteger y))))

(defrecord BigIntegerxDoubleOps []
  ITwoArityOps
  (add [_ x y]
    (.add (BigDecimal/valueOf ^double y)
          (BigDecimal. ^BigInteger x))))

(defrecord BigIntegerxBigIntOps []
  ITwoArityOps
  (add [_ x y]
    (.add ^BigInteger x (.toBigInteger ^BigInt y))))

(defrecord BigIntegerxRatioOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator y)
      (.multiply (denominator y) x))
     (denominator y))))

(defrecord BigIntegerxBigDecOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigDecimal y (BigDecimal. ^BigInteger x))))
