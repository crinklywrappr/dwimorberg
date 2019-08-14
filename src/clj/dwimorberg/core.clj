(ns dwimorberg.core
  (:require [dwimorberg.proto :as proto]
            [dwimorberg.impls :refer :all]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(defn floor ^Number [^Number n]
  (proto/floor (proto/fracSingleOps ops n) n))

(defn abs ^Number [^Number n]
  (proto/abs (proto/singleOps ops n) n))

(defn add [x y]
  (.add ^dwimorberg.proto.ITwoArityOps
        (.withTwo ^dwimorberg.proto.IWithTwo
                  (.singleOps ^dwimorberg.proto.IOps ops ^Number x)
                  ^Number y)
        ^Number x ^Number y))
