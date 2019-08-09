(ns dwimorberg.core
  (:require [dwimorberg.proto :as proto]
            [dwimorberg.impls :refer :all]))

;; (set! *warn-on-reflection* true)

(defn ^:static floor ^Number [^Number n]
  (proto/floor (proto/fracSingleOps n) n))

(defn ^:static abs ^Number [^Number n]
  (proto/abs (proto/singleOps n) n))

(defn ^:static add ^Number [^Number x ^Number y]
  (proto/add (proto/withTwo x y) x y))

;; (crit/with-progress-reporting (crit/bench (reduce add (range 1 10000)) :verbose))
;;                    Evaluation count : 111420 in 60 samples of 1857 calls.
;;          Execution time sample mean : 537.153115 µs
;;                 Execution time mean : 537.216610 µs
;; Execution time sample std-deviation : 5.153530 µs
;;        Execution time std-deviation : 5.218930 µs
;;       Execution time lower quantile : 534.988295 µs ( 2.5%)
;;       Execution time upper quantile : 545.903155 µs (97.5%)
;;                       Overhead used : 10.083285 ns
;;
;; Found 8 outliers in 60 samples (13.3333 %)
;; low-severe	 3 (5.0000 %)
;; low-mild	 5 (8.3333 %)
;; Variance from outliers : 1.6389 % Variance is slightly inflated by outliers


;; (crit/with-progress-reporting (crit/bench (reduce +' (range 1 10000)) :verbose))
;;                    Evaluation count : 592380 in 60 samples of 9873 calls.
;;          Execution time sample mean : 104.753399 µs
;;                 Execution time mean : 104.754445 µs
;; Execution time sample std-deviation : 4.471247 µs
;;        Execution time std-deviation : 4.519538 µs
;;       Execution time lower quantile : 101.408358 µs ( 2.5%)
;;       Execution time upper quantile : 116.723139 µs (97.5%)
;;                       Overhead used : 10.083285 ns
