(ns dwimorberg.impls.ratio-ops
  (:require [dwimorberg.proto :refer :all])
  (:import [clojure.lang Ratio BigInt Numbers]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(deftype RatioxRatioOps []
  ITwoArityOps
  (add [_ x y]
    (let [xnumer (numerator x)
          xdenom (denominator x)
          ynumer (numerator y)
          ydenom (denominator y)]
      (if (.equals ^BigInteger xdenom ydenom)
        (Numbers/divide (.add xnumer ynumer) xdenom)
        (Ratio.
         (.add (.multiply xnumer ydenom)
               (.multiply ynumer xdenom))
         (.multiply xdenom ydenom))))))

(deftype RatioxLongOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator x)
      (.multiply
       (denominator x)
       (BigInteger/valueOf y)))
     (denominator x))))

(deftype RatioxDoubleOps []
  ITwoArityOps
  (add [_ x y]
    (if (Common/isInt ^double y)
      (Ratio.
       (.add
        (numerator x)
        (.multiply
         (denominator x)
         (.toBigInteger (BigDecimal/valueOf ^double y))))
       (denominator y))
      (let [numer (BigDecimal. (numerator x))
            denom (BigDecimal. (denominator x))]
        (.add
         (.divide numer denom 32 RoundingMode/HALF_UP)
         (BigDecimal/valueOf ^double y))))))

(deftype RatioxBigIntegerOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator x)
      (.multiply (denominator x) y))
     (denominator x))))

(deftype RatioxBigIntOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator x)
      (.multiply
       (denominator x)
       (.toBigInteger ^BigInt y)))
     (denominator x))))

;; TODO: don't use max....
(deftype RatioxBigDecOps []
  ITwoArityOps
  (add [_ x y]
    (if (Common/isInt ^BigDecimal y)
      (Ratio.
       (.add
        (numerator x)
        (.multiply
         (denominator x)
         (.toBigInteger ^BigDecimal y)))
       (denominator x))
      (let [numer (BigDecimal. (numerator x))
            denom (BigDecimal. (denominator x))]
        (.add
         (.divide numer denom
                  (max (.precision ^BigDecimal y) 32)
                  RoundingMode/HALF_UP)
         y)))))
