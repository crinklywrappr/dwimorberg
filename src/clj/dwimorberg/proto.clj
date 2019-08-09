(ns dwimorberg.proto)

;; (set! *warn-on-reflection* true)

(defprotocol IFractionalSingleArityOps
  (^Number floor [_ ^Number n]))

(defprotocol ISingleArityOps
  (^Number abs [_ ^Number n]))

(defprotocol ITwoArityOps
  (^Number add [_ ^Number x ^Number y]))

(defprotocol IThreeArityOps
  (^Number modpow [_ ^Number x ^Number y ^Number z]))

(defprotocol IDispatch
  (^ISingleArityOps singleOps [_])
  (^IFractionalSingleArityOps fracSingleOps [_])
  (^clojure.lang.Symbol dispatchType [_]))

(defprotocol IWithTwo
  (^ITwoArityOps withTwo [_ ^IDispatch n]))

(defprotocol IWithThree
  (^IThreeArityOps withThree [_ ^IDispatch n]))
