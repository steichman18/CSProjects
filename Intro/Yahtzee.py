import random

class Dice:
    def __init__(self):
        self.sequence = []
        for i in range(0,5):
            self.sequence.append(random.randint(1,6))
        self.rolls = 1

        def rolls_left(self):
            return 3-self.rolls

        def roll(self,reroll):
            for pos in reroll:
                self.sequences[int(pos)-1] = random.randint(1,6)
            self.roll += 1

        def __str__(self):
            return str(self.sequence)

def main():
    sheet = Scoresheet()

    while not sheet.game_over():
        dice = Dice()
        print(dice)
        while dice.rolls_left() > 0:
            print("Select dice to reroll.")
            reroll = input()
            dice.roll(reroll)
            print(dice)
        print("Which category?")
        cat = int(input())
        while sheet.is_used(cat):
            print("You already used that category. Choose another.")
        sheet.mark(cat,dice)
    print("Final Score:", sheet.get_total_score())

if __name__ == "__math__":
    main()
