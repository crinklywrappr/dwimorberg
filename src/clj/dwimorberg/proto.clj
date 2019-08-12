(ns dwimorberg.proto)

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(defprotocol IFractionalSingleArityOps
  (^Number floor [_ ^Number n]))

(defprotocol ISingleArityOps
  (^Number abs [_ ^Number n]))

(defprotocol ITwoArityOps
  (^Number add [_ ^Number x ^Number y]))

(defprotocol IThreeArityOps
  (^Number modpow [_ ^Number x ^Number y ^Number z]))

(defprotocol IOps
  (^ISingleArityOps singleOps [_ ^Number n])
  (^IFractionalSingleArityOps fracSingleOps [_ ^Number n]))

(defprotocol IWithTwo
  (^ITwoArityOps withTwo [_ ^ISingleArityOps n]))

(defprotocol IWithThree
  (^IThreeArityOps withThree [_ ^ISingleArityOps n]))
