(ns dwimorberg.impls.ratio-ops
  (:import [clojure.lang Ratio BigInt Numbers]
           [java.math BigInteger BigDecimal RoundingMode MathContext]
           [dwimorberg Common]
           [dwimorberg.proto ITwoArityOps]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(defrecord RatioxRatioOps []
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

(defrecord RatioxLongOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator x)
      (.multiply
       (denominator x)
       (BigInteger/valueOf y)))
     (denominator x))))

(defrecord RatioxDoubleOps []
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

(defrecord RatioxBigIntegerOps []
  ITwoArityOps
  (add [_ x y]
    (Ratio.
     (.add
      (numerator x)
      (.multiply (denominator x) y))
     (denominator x))))

(defrecord RatioxBigIntOps []
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
(defrecord RatioxBigDecOps []
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
