


(define (myInterpreter x)
(cons  (myExpr (cdr (car x)) '()) '())
)


(define (myExpr x list)
(cond
( (integer? x) x)
( (symbol? x) (myGetValue x list)) 
( (equal? (car x) (car '(myadd))) (evalMyadd (cdr x) list) )
( (equal? (car x) (car '(mymul))) (evalMymul (cdr x) list) )
( (equal? (car x) (car '(mysub))) (evalMysub (cdr x) list) )
( (equal? (car x) (car '(myif))) (evalMyif (cdr x) list))
( (equal? (car x) (car '(mylet))) (evalMylet (cdr x) list) )
(#t (myExpr (car x) list))
)
)

(define (myGetValue x list)
(if 
(equal? x (car (car list)))
(cdr (car list))
(myGetValue x (cdr list))
)
)

(define c0 '(prog 10))
(define c1 '(prog (myadd 1 2)))
(define c2 '(prog (mymul 2 5)))
(define c3 '(prog (mysub 0 10)))
(define c4 '(prog (myif 0 5 10)))
(define c5 '(prog (mylet x 10 (myadd x x))))
(define c6 '(prog (myadd 10 (mylet x 5 (mymul x x)))))
(define c7 '(prog (mylet x (mysub 0 (mylet x 10 x)) (myadd x (mylet x 1 (myadd x x))))))
(define c8 '(prog (mylet x (mysub 0 (myadd 10 11)) (mylet y x (mymul x y)))))
(define c9 '(prog (myif (myadd 0 1) (mylet x 10 x) (mylet x 15 x))))

(define cases (list c0 c1 c2 c3 c4 c5 c6 c7 c8 c9))

; Here are the correct outputs for each case, from c0-c9
(define official '(10 3 10 -10 10 20 35 -8 441 10))


(define 
(evalMyadd x list)
(+ (myExpr (car x) list) (myExpr(car (cdr x)) list)
))

(define 
(evalMymul x list)
(* (myExpr (car x) list) (myExpr(car (cdr x)) list)
))

(define 
(evalMylet x list)
(myExpr (car (cdr (cdr x))) (uni list (cons (cons (car x) (myExpr(car (cdr x)) list) ) '()))
))

(define 
(evalMysub x list)
(- (myExpr (car x) list) (myExpr(car (cdr x)) list)
))

(define 
(evalMyif x list)
(if (equal? 0 (myExpr (car x) list)) (myExpr (car (cdr (cdr x))) list) (myExpr (car (cdr x)) list))
)


(define (uni s2 s1)
(cond 
( (null? s1) s2)
( (null? s2) s1)
( #t (cond 
( (mbr (car s1) s2) (uni (cdr s1) s2))
( #t (cons (car s1) (uni (cdr s1) s2)))))))

(define (mbr x list)
(cond
( (null? list) #f )
( #t (cond 
( (equal? x (car list)) #t )
( #t (mbr x (cdr list)) ) ) )
) )
