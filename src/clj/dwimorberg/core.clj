(ns dwimorberg.core
  (:require [dwimorberg.proto :as proto]
            [dwimorberg.impls :refer :all]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(defn floor ^Number [^Number n]
  (proto/floor (proto/fracSingleOps ops n) n))

(defn abs ^Number [^Number n]
  (proto/abs (proto/singleOps ops n) n))

(defn add ^Number [^Number x ^Number y]
  (-> ops
      (proto/singleOps x)
      (proto/withTwo y)
      (proto/add x y)))


;; with single dispatch record
;; (crit/with-progress-reporting (crit/bench (reduce add (range 1 10000)) :verbose))
;; Evaluation count : 337260 in 60 samples of 5621 calls.
;;          Execution time sample mean : 176.989471 µs
;;                 Execution time mean : 176.991319 µs
;; Execution time sample std-deviation : 1.353977 µs
;;        Execution time std-deviation : 1.380721 µs
;;       Execution time lower quantile : 175.829462 µs ( 2.5%)
;;       Execution time upper quantile : 180.492267 µs (97.5%)
;;                       Overhead used : 10.063113 ns
;; Found 9 outliers in 60 samples (15.0000 %)
;;   low-severe	 2 (3.3333 %)
;;   low-mild	 7 (11.6667 %)
;;  Variance from outliers : 1.6389 % Variance is slightly inflated by outliers


;; (crit/with-progress-reporting (crit/bench (reduce +' (range 1 10000)) :verbose))
;; Evaluation count : 592380 in 60 samples of 9873 calls.
;;          Execution time sample mean : 104.753399 µs
;;                 Execution time mean : 104.754445 µs
;; Execution time sample std-deviation : 4.471247 µs
;;        Execution time std-deviation : 4.519538 µs
;;       Execution time lower quantile : 101.408358 µs ( 2.5%)
;;       Execution time upper quantile : 116.723139 µs (97.5%)
;;                       Overhead used : 10.083285 ns
