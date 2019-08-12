(ns dwimorberg.impls.bigint-ops
  (:import [clojure.lang Ratio BigInt]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]
           [dwimorberg.proto ITwoArityOps]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(defrecord BigIntxBigIntOps []
  ITwoArityOps
  (add [_ x y] (.add ^BigInt x y)))

(defrecord BigIntxLongOps []
  ITwoArityOps
  (add [_ x y]
    (if (nil? (.bipart ^BigInt x))
      (Common/add ^long y ^long (.lpart ^BigInt x))
      (.add ^BigInteger (.bipart ^BigInt x) (BigInteger/valueOf ^long y)))))

(defrecord BigIntxDoubleOps []
  ITwoArityOps
  (add [_ x y]
    (if (nil? (.bipart ^BigInt x))
      (Common/add ^double y ^long (.lpart ^BigInt x))
      (.add (BigDecimal/valueOf ^double y)
            (BigDecimal. ^BigInteger x)))))

(defrecord BigIntxBigIntegerOps []
  ITwoArityOps
  (add [_ x y]
    (.add ^BigInteger y (.toBigInteger ^BigInt x))))

(defrecord BigIntxRatioOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator y)
      (.multiply
       (denominator y)
       (.toBigInteger ^BigInt x)))
     (denominator y))))

(defrecord BigIntxBigDecOps []
  ITwoArityOps
  (add [_ x y]
    (.add ^BigDecimal y (BigDecimal. (.toBigInteger ^BigInt x)))))
