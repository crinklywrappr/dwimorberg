(ns dwimorberg.proto)

;; (set! *warn-on-reflection* true)

;; (definterface IFractionalSingleArityOps
;;   (^Number floor [_ ^Number n]))

;; (definterface ISingleArityOps
;;   (^Number abs [_ ^Number n]))

(gen-interface
 :name dwimorberg.proto.ISingleArityOps
 :methods [ [abs [Number] Number] ])

;; (definterface ITwoArityOps
;;   (^Number add [_ ^Number x ^Number y]))

;; (definterface IThreeArityOps
;;   (^Number modpow [_ ^Number x ^Number y ^Number z]))

;; (definterface IDispatch
;;   (^ISingleArityOps singleOps [])
;;   (^IFractionalSingleArityOps fracSingleOps [])
;;   (^clojure.lang.Symbol dispatchType []))

(gen-interface
 :name dwimorberg.proto.IDispatch
 :methods [ [singleOps [] dwimorberg.proto.ISingleArityOps] ])

;; (definterface IWithTwo
;;   (^ITwoArityOps withTwo [^IDispatch n]))

;; (definterface IWithThree
;;   (^IThreeArityOps withThree [^IDispatch n]))
