#Region "###_GAME_HAND_DETECTION_ALGORITHMS_COMPONENTS_###"

    'Algorithm : Primary Poker Hand Detection.
    Private Function detectBestWinningHand()

        '##### INITIALISE LOCAL VARIABLES #####'
        Dim winPlayerIndex As Byte          'Index of the winning player.
        Dim winPlayerRank As Byte           'Winning hand rank of the winning player.
        Dim winPlayerType(1) As Byte        'Winning card type of the winning player to be compared.

        Dim suitCounter(4) As Byte          'Array to store the number of each suit when counting.

        'These variables should NOT be changed during the player loop, values are calculated and stored prior.
        Dim noChange_cardPointer As Byte    'Card Array Pointer.
        Dim noChange_cardArray(5) As Byte   'Array that stores the type of appropriate cards.

        Dim cardArrayPointer As Byte        'A card array pointer for the array that is to be used for the player loop.
        Dim cardArray(8) As Byte            'Copies the contents of the "noChange_cardArray()" to be processed for each player.
        '######################################'



        '##### ANALYSE CARD STATISTICS - PART 1 - CALCULATING "FLUSH" POSSIBILITY #####'

        'For each of the 5 community cards, count the number of suits that exist.
        For i = 1 To 5 : suitCounter(communityCards(i).suit) += 1 : Next

        'Loop for each of the 4 suits...
        For suit = 1 To 4

            'If a suit counter is bigger than or equal to 3...
            If suitCounter(suit) >= 3 Then



                '##### DETECTING HAND - ROYAL/STRAIGHT/NORMAL FLUSH #####'

                'Loop for each of the 5 community cards.
                For i = 1 To 5

                    'If the community card suit is equal to the suit that is most common (>= 3)...
                    If communityCards(i).suit = suit Then

                        'Increment 'static' card pointer by 1.
                        noChange_cardPointer += 1

                        'Add the community card type to the 'static' card array.
                        noChange_cardArray(noChange_cardPointer) = communityCards(i).type
                    End If
                Next


                'Loop for each player...
                For player = 1 To 4

                    'If the player has not folded...
                    If playerStats(player).isFolded = False Then

                        'If the either of the player's cards suit is equal to the suit that is the most common (>= 3)...
                        If (playerCards(player, 1).suit = suit) Or (playerCards(player, 2).suit = suit) Then

                            'Set the 'dynamic' array pointer value to the 'static' one.
                            cardArrayPointer = noChange_cardPointer

                            'Copy the contents of the 'static' array to the 'dynamic' card array.
                            Array.Copy(noChange_cardArray, cardArray, (cardArrayPointer + 1))


                            'For each of the player's 2 cards...
                            For i = 1 To 2
                                'If the player's card suit is equal to the target suit...
                                If playerCards(player, i).suit = suit Then

                                    'Increment 'dynamic' pointer by 1.
                                    cardArrayPointer += 1

                                    'Add player's card type to the 'dynamic' card array.
                                    cardArray(cardArrayPointer) = playerCards(player, i).type
                                End If
                            Next


                            'If the 'dynamic' card array pointer is equal to 5...
                            If cardArrayPointer >= 5 Then

                                'Sort the 'dynamic' card array with the Simple Sort algorithm.
                                simpleSortArray(cardArray, cardArrayPointer)

                                'Detect if there are 5 consecutive adjacent cards in the array, and if there 
                                'is return and store the card type of the start of the consecuive streak.
                                temp = detectConsecutiveArray(cardArray, cardArrayPointer)

                                'If 5 consecutive cards in the array was detected...
                                If temp <> 0 Then

                                    'If the 5 consecutive card streak started with an "Ace"... [14(A), 13(K), 12(Q), 11(J), 10]
                                    If temp = 14 Then
                                        winPlayerIndex = player     'Set the winning player index to the current player.
                                        winPlayerRank = 10          'Set the winning player rank to the Royal Flush Rank [10].
                                        GoTo quickEnd               'Goto the end of the algorithm, as this is the highest hand rank in the game.
                                    End If


                                    'If the start of the 5 consecutive card streak did not start with an "Ace" but a 5 consecutive card streak was still detected...
                                    'Check if the Straight Flush Rank [9] is larger than or equal to the current winning rank...
                                    If 9 >= winPlayerRank Then
                                        'Call function to compare and update the winning player statistics if necessary.
                                        updateWinningPlayerStats(player, 9, temp, 0, winPlayerIndex, winPlayerRank, winPlayerType)
                                    End If


                                Else    'If there is a NOT 5 consecutive card streak in the array.

                                    'If the Flush Rank [6] is larger than or equal to the current winning rank...
                                    If 6 >= winPlayerRank Then
                                        'Call function to compare and update the winning player statistics if necessary.
                                        updateWinningPlayerStats(player, 6, cardArray(1), 0, winPlayerIndex, winPlayerRank, winPlayerType)
                                    End If
                                End If
                            End If
                        End If
                    End If
                Next

                'If a player has successfully won a Straight Flush or Normal Flush then exit sub as this would be the highest 
                'rank available. (Although ranks 7 and 8 are higher than 6, they cant exist if some form of a Flush exists.)
                If winPlayerIndex > 0 Then GoTo quickEnd
                '########################################################'

                Exit For
            End If
        Next
        '##############################################################################'



        '##### PREPARING FOR NEXT POKER HAND COMBINATION DETECTION ALGORITHM #####'

        'Clear both card arrays should some form of Flush was possible but not sucessfully detected.
        Array.Clear(noChange_cardArray, 1, 5)
        Array.Clear(cardArray, 1, 7)

        'Set both the "Static" and "Dynamic" card index pointers to 7 (5 Community Cards + 2 Player Cards).
        noChange_cardPointer = 7
        cardArrayPointer = 7

        'For all 5 community cards in the game add the card type to the array.
        For i = 1 To 5
            noChange_cardArray(i) = communityCards(i).type
        Next
        '#########################################################################'



        'For each of the 4 players...
        For player = 1 To 4

            'If the player has not folded...
            If playerStats(player).isFolded = False Then

                'Copy the first 5 items from the "Static" to the "Dynamic" card array.
                Array.Copy(noChange_cardArray, cardArray, 6)

                'Add the current player card types to the "Dynamic" card array.
                cardArray(6) = playerCards(player, 1).type
                cardArray(7) = playerCards(player, 2).type

                'Use simple sort algorithm to sort the "Dynamic" card array.
                simpleSortArray(cardArray, cardArrayPointer)



                '##### DETECTING HAND - NORMAL STRAIGHT #####'

                'If the Straight Rank [5] is larger than or equal to the current winning player rank...
                If 5 >= winPlayerRank Then

                    'Set the temp variable to capture the starting 5 consecutive card streak if it exists.
                    temp = detectConsecutiveArray(cardArray, 7)

                    'If a 5 consecutive card streak was detected in the card array...
                    If temp <> 0 Then

                        'Call function to compare and update the winning player statistics if necessary and return indicating if an update was sucessful.
                        If updateWinningPlayerStats(player, 5, temp, 0, winPlayerIndex, winPlayerRank, winPlayerType) = True Then

                            'Skip to the next player because there is no other better type of hand possible for this player.
                            GoTo quickNextPlayer
                        End If
                    End If
                End If

                '############################################'



                '##### ANALYSE CARD STATISTICS - PART 3 - COUNT IDENTICAL CARD TYPES #####'

                'Initialise boolean variable that will determine if a 2 pair was detected.
                Dim foundIdentical As Boolean = False


                'For each of the first 6 items in the array (of 7 items)...
                For i = 1 To 6

                    'If the card array item is the same as the next adjacent array item...
                    If cardArray(i) = cardArray(i + 1) Then
                        foundIdentical = True       'Set boolean variable to indicate that a pair has been detected.
                        Exit For                    'This was only a quick pair detection, and so if a pair exists exit for loop.
                    End If
                Next


                'If a piar was detected...
                If foundIdentical = True Then
                    Dim pairType(3) As Byte         'Initialise array that stores the card types of the multiple card types.
                    Dim pairCounter(3) As Byte      'Initialise array that counts the number of identical card types.
                    Dim tPointer As Byte = 1        'Initialise variable that acts as a temporary "stack tracker".


                    'Loop 3 times... (because there can only be a maximum of 3 (2x) pairs in a set of 7 cards at any one time)
                    For i = 1 To 3

                        'If 7 minus the stack tracker is greater than or equal to 2...
                        If (7 - tPointer) >= 2 Then

                            'Count the number of continuous identical card types...
                            countIdenticalCardTypes(cardArray, pairType(i), pairCounter(i), tPointer)

                        Else : Exit For     'Once there cant be anymore pairs possible then exit the loop early.
                        End If
                    Next


                    'Initialise boolean variable that will indicate whether or not a 3 identical card pair was detected.
                    Dim found3Pair As Boolean = False

                    'Initialise a byte variable that will count the number of 2 identical card pairs.
                    Dim pair2Counter As Byte = 0

                    'Stores the highest 2 pair card type to be used to compare if multiple players have equal conflicting hands.
                    Dim pair2Type As Byte = 0

                    'Stores the highest 3 pair card type to be used to compare if multiple players have equal conflicting hands.
                    Dim pair3Type As Byte = 0


                    'For each of the 3 items of the array that keeps track of the number of identical card types...
                    For i = 1 To 3

                        Select Case pairCounter(i)
                            'If there is a 2 identical card pair...
                            Case 2
                                'Increment the 2 identical card pair counter by 1.
                                pair2Counter += 1

                                'If the current 2 pair type is bigger than the highest 2 pair type, then store the new highest 2 pair type.
                                If pairType(i) > pair2Type Then pair2Type = pairType(i)



                                'If there is a 3 identical card pair...
                            Case 3
                                'Set the found3Pair variable to true to indicate that a 3 identical card pair has been found.
                                found3Pair = True

                                'If the current 3 pair type is bigger than the highest 3 pair type, then store the new highest 3 pair type.
                                If pairType(i) > pair3Type Then pair3Type = pairType(i)



                            Case 4
                                '##### DETECTING HAND - FOUR OF A KIND #####'

                                'If the 4 Of A Kind Rank [8] is larger than or equal to the current winning player rank...
                                If 8 >= winPlayerRank Then

                                    'Call function to compare and update the winning player statistics if necessary and return indicating if an update was sucessful.
                                    If updateWinningPlayerStats(player, 8, pairType(i), 0, winPlayerIndex, winPlayerRank, winPlayerType) = True Then

                                        'Skip to the next player because there is no other better type of hand possible for this player.
                                        GoTo quickNextPlayer
                                    End If
                                End If
                                '###########################################'


                            Case 0 : Exit For       'Once there are no more pairs detected then exit the loop early.
                        End Select
                    Next



                    '##### DETECTING HAND - FULL HOUSE / THREE OF A KIND #####'

                    'If a 3 identical card pair was found...
                    If found3Pair = True Then

                        'If a 2 identical card pair was found...
                        If pair2Counter > 0 Then

                            'If the Full House Rank [7] is larger than or equal to the current winning player rank...
                            If 7 >= winPlayerRank Then

                                'Call function to compare and update the winning player statistics if necessary and return indicating if an update was sucessful.
                                If updateWinningPlayerStats(player, 7, pair3Type, pair2Type, winPlayerIndex, winPlayerRank, winPlayerType) = True Then

                                    'Skip to the next player.
                                    GoTo quickNextPlayer
                                End If
                            End If



                        Else    'if no 2 identical card pair was found, but a 3 card pair was...

                            'If the 3 Of a Kind Rank [4] is larger than or equal to the current winning player rank...
                            If 4 >= winPlayerRank Then

                                'For each of the 7 cards in the card array...
                                For i = 1 To 7

                                    'If the card (in the card array) does not equal one of the cards that make the 3 Of A Kind Hand...
                                    If cardArray(i) <> pair3Type Then
                                        temp = cardArray(i)     'Set the temp variable to that highest card that isnt part of the 3 Of a Kind.
                                        Exit For                'Exit the For Loop.
                                    End If
                                Next


                                'Call function to compare and update the winning player statistics if necessary and return indicating if an update was sucessful.
                                If updateWinningPlayerStats(player, 4, pair3Type, temp, winPlayerIndex, winPlayerRank, winPlayerType) = True Then

                                    'Skip to the next player.
                                    GoTo quickNextPlayer
                                End If
                            End If
                        End If
                    End If

                    '#########################################################'



                    '##### DETECTING HAND - ONE PAIR / TWO PAIR #####'
                    Select Case pair2Counter
                        Case 1      'When there is only 1 x 2 identical card pair...

                            'If the One Pair Rank [2] is larger than or equal to the current winning player rank...
                            If 2 >= winPlayerRank Then

                                'Call function to compare and update the winning player statistics if necessary and return indicating if an update was sucessful.
                                If updateWinningPlayerStats(player, 2, pair2Type, 0, winPlayerIndex, winPlayerRank, winPlayerType) = True Then

                                    'Skip to the next player.
                                    GoTo quickNextPlayer
                                End If
                            End If



                        Case Is > 1     'When there is multiple 2 identical card pairs...

                            'If the Two Pair Rank [3] is larger than or equal to the current winning player rank...
                            If 3 >= winPlayerRank Then

                                temp = pairType(2)  'Set the temp variable to hold the second highest 2 pair card type.

                                'Call function to compare and update the winning player statistics if necessary and return indicating if an update was sucessful.
                                If updateWinningPlayerStats(player, 3, pair2Type, temp, winPlayerIndex, winPlayerRank, winPlayerType) = True Then

                                    'Skip to the next player.
                                    GoTo quickNextPlayer
                                End If
                            End If
                    End Select
                    '################################################'

                End If
                '#########################################################################'




                '##### DECTECTING HAND - HIGH CARD #####'

                'If the High Card Rank [1] is larger than or equal to the current winning player rank...
                If 1 >= winPlayerRank Then

                    'If the player's first card type is smaller than the second card type...
                    If playerCards(player, 1).type < playerCards(player, 2).type Then

                        'Call function to compare and update the winning player statistics if necessary.
                        updateWinningPlayerStats(player, 1, playerCards(player, 2).type, playerCards(player, 1).type, winPlayerIndex, winPlayerRank, winPlayerType)


                    Else    'if the players first card is bigger than or equal to...

                        'Call function to compare and update the winning player statistics if necessary.
                        updateWinningPlayerStats(player, 1, playerCards(player, 1).type, playerCards(player, 2).type, winPlayerIndex, winPlayerRank, winPlayerType)
                    End If
                End If

                '#######################################'

            End If


            'A 'GOTO' label that allows parts of the program to quickly jump to this part of the code to 
            'skip to the next player when the best hand for the current player has already been found.
quickNextPlayer:
        Next


        'Another 'GOTO' label that is only used when a player has a ROYAL FLUSH [RANK: 10] to skip to 
        'the very end as this is the highest rank in the entire game and connot be beaten.
quickEnd:


        'Display appropriate message according to the winning player's hand rank...
        Select Case winPlayerRank

            'RANK [2] - MESSAGE: 1 PAIR!
            Case 2 : MsgBox("PLAYER " & winPlayerIndex & ": ONE PAIR!")

                'RANK [3] - MESSAGE: 2 PAIR!
            Case 3 : MsgBox("PLAYER " & winPlayerIndex & ": TWO PAIR!")

                'RANK [4] - MESSAGE: 3 OF A KIND!
            Case 4 : MsgBox("PLAYER " & winPlayerIndex & ": THREE OF A KIND!")

                'RANK [5] - MESSAGE: STRAIGHT!
            Case 5 : MsgBox("PLAYER " & winPlayerIndex & ": STRAIGHT!")

                'RANK [6] - MESSAGE: FLUSH!
            Case 6 : MsgBox("PLAYER " & winPlayerIndex & ": FLUSH!")

                'RANK [7] - MESSAGE: FULL HOUSE!
            Case 7 : MsgBox("PLAYER " & winPlayerIndex & ": FULL HOUSE!")

                'RANK [8] - MESSAGE: 4 OF A KIND!
            Case 8 : MsgBox("PLAYER " & winPlayerIndex & ": FOUR OF A KIND!")

                'RANK [9] - MESSAGE: STRAIGHT FLUSH!
            Case 9 : MsgBox("PLAYER " & winPlayerIndex & ": STRAIGHT FLUSH!")

                'RANK [10] - MESSAGE: ROYAL FLUSH!
            Case 10 : MsgBox("PLAYER " & winPlayerIndex & ": ROYAL FLUSH!")

                'ELSE : RANK [1] - MESSAGE: HIGH CARD  
            Case Else : MsgBox("PLAYER " & winPlayerIndex & ": HIGH CARD!")
        End Select


        'Return the index of the player that won (with the highest ranking hand).
        Return winPlayerIndex
    End Function


    'Algorithm: Simple Sort Array.
    Private Sub simpleSortArray(ByRef array() As Byte, ByRef pointer As Byte)

        'For each of the items in the array excluding the last item...
        For i = 1 To (pointer - 1)

            'For each of the other items in the array (including the last item)...
            For j = (i + 1) To pointer

                'If the LEFT item is small than the RIGHT item in the array...
                If array(i) < array(j) Then
                    temp = array(i)         'Use the global temp variable to hold the LEFT item.
                    array(i) = array(j)     'Set the LEFT item to the value of the RIGHT item.
                    array(j) = temp         'Set the RIGHT item to the value of the temp variable (LEFT item).
                End If
            Next
        Next
    End Sub


    'Algorithm: Detect 5 Consecutive Cards in the Card Array.
    Private Function detectConsecutiveArray(ByRef array() As Byte, ByRef pointer As Byte)

        'Initialise new "Corrected" Pointer that is required if there is an "Ace" in the array, 
        'because the value of the "Ace" in this program only exists as the value 14 but in reality 
        'it is also treated as the value 1 and so certain adjustments are required.
        Dim cPointer As Byte = pointer


        'If the first item in the array (sorted in decending order, so highest card is first) is an "Ace"...
        If array(1) = 14 Then

            'Set the "Special Index (8)" of the card array to 1 (which is treated as an "Ace" as well). 
            array(pointer + 1) = 1

            'Increment the "Corrected Pointer" by 1, so that it will include the Special value 1 (Ace).
            cPointer += 1
        End If


        'Initialise boolean array that will indicate if the last 3 of the 5 consecutive card streak was detected.
        'This is to ensure that the multiple cards of the same type are not counted twice.
        Dim numCheck(2) As Boolean

        'Initialise variable that will count if the last 3 cards of the 5 consecutive card streak was detected.
        Dim numCounter As Byte


        'For each of the cards in the array excluding the last 4 (due to detecting a 5 consecutive card streak)...
        For i = 1 To (cPointer - 4)

            'Initialise the 3 consecutive card counter to 0 at the start of the most outer loop.
            numCounter = 0

            'If the current value of the item in the array equal the next item with 1 added on (eg. [1]:10 = [2]:(9+1))...
            If array(i) = (array(i + 1) + 1) Then

                'For each of the items from the previous "Next" item to the end of the array...
                For j = (i + 2) To cPointer

                    'Compare the value of the 3 item of the 5 consecutice card streak...
                    Select Case array(j)

                        'If the value of the 3 item is equal to the value of the first item minus 2 (eg. [3]:8 = [1]:10-2).
                        Case (array(i) - 2)
                            'If the 3rd item detected boolean variable equals FALSE.
                            If numCheck(0) = False Then
                                numCheck(0) = True      'Set the 3rd item detected boolean variable to TRUE.
                                numCounter += 1         'Increment the 3 consecutive card counter by 1.
                            End If



                            'If the value of the 3 item is equal to the value of the first item minus 3 (eg. [4]:7 = [1]:10-3).
                        Case (array(i) - 3)
                            'If the 4th item detected boolean variable equals FALSE.
                            If numCheck(1) = False Then
                                numCheck(1) = True      'Set the 4th item detected boolean variable to TRUE.
                                numCounter += 1         'Increment the 3 consecutive card counter by 1.
                            End If



                            'If the value of the 3 item is equal to the value of the first item minus 4 (eg. [5]:6 = [1]:10-4).
                        Case (array(i) - 4)
                            'If the 5th item detected boolean variable equals FALSE.
                            If numCheck(2) = False Then
                                numCheck(2) = True      'Set the 5th item detected boolean variable to TRUE.
                                numCounter += 1         'Increment the 3 consecutive card counter by 1.
                            End If
                    End Select
                Next

                'If the 3 consecutive card counter equals 3 (5 consecutive card streak) then return 
                'the card type of the start of the streak.
                If numCounter = 3 Then Return array(i)
            End If
        Next


        'If no 5 consecutive card streak was detected in the card array, return the integer 0.
        Return 0
    End Function


    'Algorithm: Detect Multiples of the Identical Card Types [Count and Stores the Type(s)].
    Private Sub countIdenticalCardTypes(ByRef array() As Byte, ByRef type As Byte, ByRef counter As Byte, ByRef tPointer As Byte)

        'For each of the items in the card array starting from a specified point to the  6 item item 
        '(excludes the last item as it is detecting pairs).
        For i = tPointer To 6

            tPointer = i    'Update pointer to the new position of the array.

            'If the current item in the array is the same as the next item (Pair Found)...
            If array(i) = array(i + 1) Then
                type = array(i)     'Set the pair type variable to the to the current card type.
                counter = 1         'Set the pair counter to 1.

                'For each of the items of from the the next item from the current to the end of the array...
                For j = (i + 1) To 7

                    'If the cards match...
                    If array(i) = array(j) Then
                        counter += 1    'Increment the identical pair counter by 1.
                        tPointer = j    'Update pointer to the new current position.

                    Else : Exit For     'Once the cards no longer match, then exit the loop.
                    End If
                Next

                Exit For    'Exit the outer FOR loop after 1 pair counting pass.
            End If
        Next
    End Sub


    'Algorithm: Compare Potential New Winning Player and Update the Winning Player Statistics if Necessary.
    Private Function updateWinningPlayerStats(ByRef currentPlayer As Byte, ByRef currentHandRank As Byte, ByRef primaryCurrentHandValue As Byte, ByRef secondaryCurrentHandValue As Byte, ByRef winPlayer As Byte, ByRef winHandRank As Byte, ByRef winHandValue() As Byte)
        Select Case currentHandRank     'Compare the value of the provided hand rank...
            Case Is = winHandRank       'If the provided hand rank is the same as the current winning hand rank...

                'Compare the value of the provided primary hand value/type...
                Select Case primaryCurrentHandValue

                    'If the the provided primary hand value is the same as the winning primary hand value...
                    Case Is = winHandValue(0)

                        'If the provided secondary hand value is bigger than the winning secondary hand value...
                        If secondaryCurrentHandValue > winHandValue(1) Then

                            'Set the winning player to the current player.
                            winPlayer = currentPlayer

                            'Set the winning primary hand value to the current primary hand value.
                            winHandValue(0) = primaryCurrentHandValue

                            'Set the winning secondary hand value to the current secondary hand value.
                            winHandValue(1) = secondaryCurrentHandValue

                            'Return TRUE to indicate that the winning player stats has been changed/updated.
                            Return True
                        End If


                        'If the provided primary hand value is bigger than the winning primary hand value.
                    Case Is > winHandValue(0)

                        'Set the winning player to the current player.
                        winPlayer = currentPlayer

                        'Set the winning primary hand value to the current primary hand value.
                        winHandValue(0) = primaryCurrentHandValue

                        'Set the winning secondary hand value to the current secondary hand value.
                        winHandValue(1) = secondaryCurrentHandValue

                        'Return TRUE to indicate that the winning player stats has been changed/updated.
                        Return True
                End Select



                'If the provided hand rank is bigger than the currently winning player hand rank...
            Case Is > winHandRank

                'Set the winning player to the current player.
                winPlayer = currentPlayer

                'Set the winning hand rank to the newly provided hand rank.
                winHandRank = currentHandRank

                'Set the winning primary hand value to the current primary hand value.
                winHandValue(0) = primaryCurrentHandValue

                'Set the winning secondary hand value to the current secondary hand value.
                winHandValue(1) = secondaryCurrentHandValue

                'Return TRUE to indicate that the winning player stats has been changed/updated.
                Return True
        End Select


        'Return FALSE to indicate that the winning player stats has NOT been changed/updated.
        Return False
    End Function

#End Region
