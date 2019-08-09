(ns dwimorberg.core-test
  (:require [clojure.test :refer :all]
            [dwimorberg.core :refer :all])
  (:import [dwimorberg Common]))

(deftest long-add-test
  (testing "adding different types to longs"
    (is (= 3 (add 1 2)))
    (is (= 18446744073709551614 (add Long/MAX_VALUE Long/MAX_VALUE)))
    (is (= 3.234 (add 1 2.234)))
    (is (instance? BigDecimal (add 1 Double/MAX_VALUE)))
    (is (= 12/7 (add 1 5/7)))
    (is (= 3.234M (add 1 2.234M)))))

(deftest double-add-test
  (testing "adding different types to doubles"
    (is (= 3.234 (add 2.234 1)))
    (is (= 3.5 (add 1.2 2.3)))
    (is (= 9223372036854775808 (add Long/MAX_VALUE 1)))
    (is (= 9223372036854775808.234M (add Long/MAX_VALUE 1.234)))
    (is (= 12/7 (add 1.0 5/7)))
    (is (= 1.91428571428571428571428571428571M (add 1.2 5/7)))
    (is (= 3.345M (add 1.0 2.345M)))))

(deftest biginteger-add-test
  (testing "adding different types to bigintegers"
    (is (= (biginteger 3) (add (biginteger 1) (biginteger 2))))
    (is (= (biginteger 3) (add (biginteger 1) 2)))
    (is (= 3.456M (add (biginteger 1) 2.456)))
    (is (= (biginteger 3) (add (biginteger 1) 2N)))
    (is (= 12/7 (add (biginteger 1) 5/7)))
    (is (= 3.456M (add (biginteger 1) 2.456M)))))

(deftest bigint-add-test
  (testing "adding different types to bigints"
    (is (= 3 (add 1N 2)))
    (is (= (biginteger 100000000000000000000)
           (add 99999999999999999999N 1)))
    (is (= 3N (add 1N 2N)))
    (is (= 12/7 (add 1N 5/7)))
    (is (= 3.345 (add 1N 2.345)))
    (is (= 3.345M (add 1N 2.345M)))
    (is (= 9007199254740992.345M
           (add 1N (add (dec dwimorberg.Common/TOP_DOUBLE) 0.345))))))
